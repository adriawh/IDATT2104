#include <iostream>
#include <thread>
#include <functional>
#include <list> 
#include <vector> 
#include <mutex>

using namespace std;

int main(){

    {

    }


}


class PrimeFinder{

    int start;
    int end;
    int numberOfThreads;
    vector<thread> threads;
    list<int> primes;
    mutex primeFond_mutex;

    public:
        PrimeFinder(int start, int end, int numberOfThreads){
            this->start = start;
            this->end = end;
            this->numberOfThreads = numberOfThreads;
        }
        
    list<int> findPrimes(int start, int end, int numberOfThreads){

    for(int i = 0; i<numberOfThreads; i++){

      
    }
            
   



        
        for(int i = start; i<end; i++){
            if(isPrime(i)) primes.insert(primes.end(), 1, i);
        }

        primes.sort();

        return primes;

    }
    
    
    bool isPrime(int n){
        if (n == 0 || n == 1) return false;
            
        for (int i = 2; i <= n / 2; ++i){
            if (n % i == 0) return false;
        }
    
        return true;
    }


};