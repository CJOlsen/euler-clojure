;; Author: Christopher Olsen

;; Project Euler Problem 6
;; 
;; Problem Statement:
;; The sum of the squares of the first ten natural numbers is,
;; 1^2 + 2^2 + ... + 10^2 = 385
;;
;; The square of the sum of the first ten natural numbers is,
;; (1 + 2 + ... + 10)2 = 552 = 3025
;;
;; Hence the difference between the sum of the squares of the first ten 
;; natural numbers and the square of the sum is 3025 − 385 = 2640.
;;
;; Find the difference between the sum of the squares of the first one 
;; hundred natural numbers and the square of the sum.


;; The Plan
;;
;; - write a square function
;; - sum of squares:
;;     use reduce with + over the mapping of square over the needed range 
;; - square of sum:
;;     apply square to the result of using reduce with + over the range
;; - subtract the former from the latter

(defn square [number]
  (* number number))

(defn prob6 [max]
  (- (square (reduce + (range 1 (inc max)))) ;; square of sum
     (reduce + (map square (range 1 (inc max)))))) ;;sum of squares

(time (prob6 100))
;; "Elapsed time: 13.53573 msecs"
;; 25164150
