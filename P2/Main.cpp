

#include <condition_variable>
#include <thread>
#include <list>
#include <iostream>

using namespace std;

class Workers {

    condition_variable cv;
    int number_of_threads;

    list<function<void()>> tasks;
    list<thread> threads;

    mutex tasks_mutex;

    bool run = true;
    bool wait = true;


public:
    Workers(int number_of_threads){
        this->number_of_threads = number_of_threads;
    }

    void start(){
        for(int i =0; i<number_of_threads; i++) {

            threads.emplace_back([this] {
                while(run){
                    function<void()> task;
                    {
                        unique_lock<mutex> lock(tasks_mutex);
                        while(wait){
                            cv.wait(lock);
                        }

                        if(!tasks.empty()) {
                            task = *tasks.begin();
                            tasks.pop_front();
                        }

                    }
                    if(task){
                        task();
                    }
                }
            });

            {
                unique_lock<mutex> lock(tasks_mutex);
                wait=false;
            }
            cv.notify_one();
        }
    }

    void post(function<void()> task){
        unique_lock<mutex> lock(tasks_mutex);
        tasks.emplace_back(task);
        cv.notify_one();
    }

    void post_timeout(function<void()> task){
        unique_lock<mutex> lock(tasks_mutex);
        tasks.emplace_back([task] {
            this_thread::sleep_for(1000ms);
            task();
        });
        cv.notify_one();
    }

    void stop(){
        while(run){
            if(tasks.empty()){
                run = false;
            }
        }
    }

    void join(){
        for(auto &thread : threads){
            thread.join();
        }
    }
};


int main(){

    Workers worker_threads(4);
    Workers event_loop(1);

    worker_threads.start();
    event_loop.start();


    worker_threads.post([]{
        cout << "A: Running in parallel" << endl;
    });

    worker_threads.post_timeout([]{
        cout << "B: I should come last" << endl;
    });

    worker_threads.post([]{
        cout << "C: Running in parallel" << endl;
    });

    event_loop.post([]{
        cout << "D: I come before E" << endl;
    });

    event_loop.post([]{
        cout << "E: I come after D" << endl;
    });


    worker_threads.stop();
    event_loop.stop();

    worker_threads.join();
    event_loop.join();
}