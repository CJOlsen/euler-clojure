;; Author: Christopher Olsen

;; Project Euler Problem 25
;;
;; 1000-digit Fibonacci number
;;
;; The Fibonacci sequence is defined by the recurrence relation:
;;
;;    Fn = Fn−1 + Fn−2, where F1 = 1 and F2 = 1.
;;
;; Hence the first 12 terms will be:
;; 
;;     F1 = 1
;;     F2 = 1
;;     F3 = 2
;;     F4 = 3
;;     F5 = 5
;;     F6 = 8
;;     F7 = 13
;;     F8 = 21
;;     F9 = 34
;;     F10 = 55
;;     F11 = 89
;;     F12 = 144
;;
;; The 12th term, F12, is the first term to contain three digits.
;;
;; What is the first term in the Fibonacci sequence to contain 1000 digits?


;; The Plan
;;
;; -Use loop/recur to write a tail-call optimized recursive definition that
;;  will step through the Fibonacci sequence and test the length of the 
;;  current item as it goes


(defn problem-25
  []
  (loop [back-1 1N ;; "N" makes these big integers, since they will be
         back-2 1N
         counter 2]
    (if (>= (count (str back-2)) 1000)
      counter
      (recur back-2 (+ back-1 back-2) (inc counter)))))

(time (problem-25))
;; "Elapsed time: 1183.961521 msecs"
;; 4782
