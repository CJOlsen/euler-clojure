;; Author: Christopher Olsen

;; Project Euler Problem 16
;;
;; Power Digit Sum
;;
;; 2^15 = 32768 and the sum of its digits is 3 + 2 + 7 + 6 + 8 = 26.
;;
;; What is the sum of the digits of the number 2^1000?


;; The Plan
;;
;; Not much planning for this one...just leverage type conversions and try to
;; keep away from overflow errors
;;

(defn problem-16 []
(-> (reduce * (repeat 1000 2N))     ;; get 2^1000 (notice the N, it's necessary)
    str                             ;; cast it to a string
    (#(clojure.string/split % #"")) ;; split it into characters
    rest                            ;; trim the first character (it's extra)
    (#(map read-string %))          ;; back to integers
    (#(reduce + %))))               ;; add 'em up


(time (problem-16))
;; "Elapsed time: 12.403952 msecs"
;; 1366
