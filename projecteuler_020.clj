;; Author: Christopher Olsen

;; Project Euler Problem 20
;;
;; Factorial Digit Sum
;;
;; n! means n × (n − 1) × ... × 3 × 2 × 1
;;
;; For example, 10! = 10 × 9 × ... × 3 × 2 × 1 = 3628800,
;; and the sum of the digits in the number 10! is 
;;     3 + 6 + 2 + 8 + 8 + 0 + 0 = 27.
;;
;; Find the sum of the digits in the number 100!
;;

;; The Plan
;;
;; - modify problem 16's solution :) 
;; - use bigint to avoid the integer overflow problem

(defn problem-20 []
  (-> (reduce * (map bigint (range 1 101))) ;; get 100!
      str                             ;; cast it to a string
      (#(clojure.string/split % #"")) ;; split it into characters
      rest                            ;; trim the first character (it's extra)
      (#(map read-string %))          ;; back to integers
      (#(reduce + %))))               ;; add 'em up

(time (problem-20))
;; "Elapsed time: 12.018917 msecs"
;; 648
