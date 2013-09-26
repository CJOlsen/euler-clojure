;; Author: Christopher Olsen

;; Project Euler Problem 5
;;
;; Problem Statement:
;; 2520 is the smallest number that can be divided by each of the numbers 
;; from 1 to 10 without any remainder.
;;
;; What is the smallest positive number that is evenly divisible by all of 
;; the numbers from 1 to 20?


;; The Plan
;;
;; We don't need to check all of the numbers (all numbers divisible by 20 will
;;  be divisible by 10, 5, 4, and 2)
;;
;; So we can check multiples of 20 that are divisible by:
;;  19, 18, 17, 16, 15, 14, 13, 12, 11 (ten through 1 will always be true if 
;;                                      the rest hold)

(defn problem5 []
  (def divisors [19 18 17 16 15 14 13 12 11])
  (loop [val 20]
    (if (every? identity (map #(zero? (mod val %)) divisors))
      val
      (recur (+ val 20)))))

(time (problem5))
;; "Elapsed time: 112542.142198 msecs"
;; 232792560



;; The New Plan
;;
;; Seems kind of wasteful to always check all of the divisors even after some 
;; are known to be false.  So this time we roll our own loop that we can kick 
;; out of at the first false (i.e. the loop will only get to checking 18 one 
;; out of every 19 times because that's how often a multiple of 20 is also a 
;; multiple of 19)

(defn prob5 []
  (def divisors [19 18 17 16 15 14 13 12 11])
  (loop [val 20]
    (if (loop [current-divs divisors]
          ;; this loop avoids checking more divisors than necessary
          (cond (empty? current-divs)
                true
                (zero? (mod val (first current-divs)))
                (recur (rest current-divs))
                :else
                false))
      val
      (recur (+ val 20)))))

(time (prob5))
;; "Elapsed time: 14497.7186 msecs"
;; 232792560
;;
;; an order of magnitude faster!


;;
;; and with the inner loop written seperately...for clarity?  
;;

(defn prob5 []
  (def divisors [19 18 17 16 15 14 13 12 11])
  (defn inner-loop [val]
    (loop [current-divs divisors]
          ;; this loop avoids checking more divisors than necessary
          (cond (empty? current-divs) true
                (zero? (mod val (first current-divs))) (recur
                                                        (rest current-divs))
                :else false)))
  (loop [val 20]
    (if (inner-loop val)
      val
      (recur (+ val 20)))))

(time (prob5))
;; "Elapsed time: 13832.692062 msecs"
;; 232792560
