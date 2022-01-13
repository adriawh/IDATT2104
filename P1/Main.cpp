#include <iostream>
#include <thread>
#include <list> 
#include <vector>

using namespace std;

bool isPrime(int n){
    if (n == 0 || n == 1) return false;

    for (int i = 2; i <= n / 2; ++i){
        if (n % i == 0) return false;
    }

    return true;
}

int main(){

    int start = 0;
    int end = 100;
    int numberOfThreads = 10;
    vector<thread> threads;
    list<int> primes;
    mutex primeFoundMutex;

    int blockSize = (end-start)/numberOfThreads;

    int currentIndex;

    for(int i = 0; i < numberOfThreads; i++){

        threads.emplace_back([i, &start, &primes, &primeFoundMutex, &blockSize, &currentIndex, &end] {

            for(int j = start + i*blockSize; j < start + (i+1) * blockSize; j++){

                if(isPrime(j)){
                    primeFoundMutex.lock();
                    primes.push_back(j);
                    primeFoundMutex.unlock();
                }
            }
        });
    }

    for(int i = 0; i<threads.size(); i++){
        threads[i].join();
    }

    primes.sort();

    for(auto i : primes){
       std:: cout << i  << ' ';
    }
}

