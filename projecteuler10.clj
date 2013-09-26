;; Author: Christopher Olsen

;; Project Euler Problem 10
;;
;; The Summation Of Primes:
;; The sum of the primes below 10 is 2 + 3 + 5 + 7 = 17.
;;
;; Find the sum of all the primes below two million.


;; The Plan
;;
;; -Use the prime? function from Problem 7 instead of making a seive
;; -for the range 1-2,000,000, filter by prime? and use (reduce + ) to keep
;;  a running sum

;; from Problem 7
(defn prime? [number]
  ;; with Math/sqrt instead of rough-sqrt
  (cond (= number 1) false
        (= number 2) true
        (even? number) false ;; because of this we can iterate through the odds
        :else
        (do (def maximum (Math/sqrt number))
            (loop [divisor 3]
              (cond (> divisor maximum) true
                    (zero? (mod number divisor)) false
                    :else (recur (+ divisor 2)))))))

(defn prob10
  ([]
     (reduce + (filter prime? (range 1 2000000))))  
  ([max]
     (reduce + (filter prime? (range 1 max)))))
  
;; here we run through just to get a look at the time growth, this is getting
;; into the range where a prime-seive may prove it's usefulness

(time (prob10 10))
;; "Elapsed time: 1.33774 msecs"
;; 17

(time (prob10 100))
;; "Elapsed time: 9.8655 msecs"
;; 1060

(time (prob10 1000))
;; "Elapsed time: 9.88743 msecs"
;; 76127

(time (prob10 10000))
;; "Elapsed time: 53.529084 msecs"
;; 5736396

(time (prob10 100000))
;; "Elapsed time: 514.039879 msecs"
;; 454396537

(time (prob10 1000000))
;; "Elapsed time: 12032.868146 msecs"
;; 37550402023

(time (prob10 2000000))
;; "Elapsed time: 31154.428647 msecs"
;; 142913828922
