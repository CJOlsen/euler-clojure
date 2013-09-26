;; Author: Christopher Olsen

;; Project Euler Problem 1
;;
;; Problem Statement:
;; If we list all the natural numbers below 10 that are multiples of 3 or 5, 
;; we get 3, 5, 6 and 9. The sum of these multiples is 23.
;;
;; Find the sum of all the multiples of 3 or 5 below 1000.


;; The Plan
;;
;; filter using a (div3 or div5)? function over the range of 1-1000
;; reduce with '+' to get the sum

(defn div-by-3-or-5?
  [number]
  (or (zero? (mod number 3))
      (zero? (mod number 5))))

(time (reduce + (filter div-by-3-or-5? (range 1 1000))))

;; "Elapsed time: 2.005562 msecs"
;; 233168
