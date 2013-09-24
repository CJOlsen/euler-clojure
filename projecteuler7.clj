;; Author: Christopher Olsen

;; Project Euler Problem 7
;;
;; Problem Statement:
;; By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, we can see 
;; that the 6th prime is 13.
;;
;; What is the 10 001st prime number?


;; The Plan
;;
;; Recursively step through the natural numbers looking for primes
;; when each is found, increment the count
;; when the count equals 10,001 return the current prime
;;
;; Use tail recursion to keep the memory footprint down (i.e. don't store
;; 10,000 primes which will probably overflow the stack)
;;
;; For the prime? function to run optimally we need a square root function,
;; and for this to run with only the core library we'll use Newton's Method
;; adapted from the SICP course (if you don't know what SICP is look it up!)
;; (Math/sqrt is used for comparison at the end)


;;
;; rolling our own square-root
;;

(defn abs [number]
  ;; absolute value
  (if (< number 0)
    (- 0 number)
    number))

(defn good-enough? [guess x]
  (< (abs (- (* guess guess) x))
     1)) ;; we're just using 1 for the tolerance since we only care about ints

(defn improve [guess x]
  (/ (+ guess (/ x guess))
     2))

(defn sqrt-iter [guess x]
  ;; why doesn't clojure handle tail optimization?  It's painful....
  (loop [g guess
         ex x]
    (if (good-enough? g ex)
      g
      (recur (improve g ex)
             ex))))

(defn rough-sqrt [x]
  ;; returns a rough square root (on the high side, generally one over)
  (int (+ (quot (sqrt-iter 1.0 x) 1)
          1)))

;;
;; prime check
;;

(defn prime? [number]
  (cond (= number 1) true
        (even? number) false ;; because of this we can iterate through the odds
        :else
        (do (def maximum (rough-sqrt number))
            (loop [divisor 3]
              (cond (> divisor maximum) true
                    (zero? (mod number divisor)) false
                    :else (recur (+ divisor 2)))))))

(defn prob7 []
  (loop [current 1
         counter 0]
    (cond (= counter 10001) (- current 1) 
          (prime? current) (recur (inc current)
                                  (inc counter))
          :else (recur (inc current)
                       counter))))

(time (prob7))
;; "Elapsed time: 1974.651385 msecs"
;; 104743


;;
;; let's see how much the self-rolled sqrt is costing us in time....
;;

(defn prime?-markII [number]
  ;; with Math/sqrt instead of rough-sqrt
  (cond (= number 1) true
        (even? number) false ;; because of this we can iterate through the odds
        :else
        (do (def maximum (Math/sqrt number))
            (loop [divisor 3]
              (cond (> divisor maximum) true
                    (zero? (mod number divisor)) false
                    :else (recur (+ divisor 2)))))))

(defn prob7-markII []
  (loop [current 1
         counter 0]
    (cond (= counter 10001) (- current 1) 
          (prime?-markII current) (recur (inc current)
                                  (inc counter))
          :else (recur (inc current)
                       counter))))

(time (prob7-markII))
;; "Elapsed time: 1112.595882 msecs"
;; 104743

;; so self-rolled square root not as fast as the Math library