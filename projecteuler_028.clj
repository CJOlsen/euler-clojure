;; Author: Christopher Olsen
;;
;; Project Euler Problem 28
;;
;; Number Spiral Diagonals
;;
;; 
;; Starting with the number 1 and moving to the right in a clockwise direction 
;; a 5 by 5 spiral is formed as follows:
;;
;; 21 22 23 24 25
;; 20  7  8  9 10
;; 19  6  1  2 11
;; 18  5  4  3 12
;; 17 16 15 14 13
;;
;; It can be verified that the sum of the numbers on the diagonals is 101.
;;
;; What is the sum of the numbers on the diagonals in a 1001 by 1001 spiral 
;; formed in the same way?

;; The Plan
;;
;; - there's a pattern: start at 1, the next 4 numbers are from adding 2, the
;;   4 after that are from adding 6, etc.
;; - there are also (n-1)/2 rings, so a 1001x1001 spiral has 500 rings
;; - use a tail-call optimized recursive algorithm to step through just the
;;   diagonal numbers and add them to the total

(defn problem-28
  []
  (loop [increment 2
         current 3
         sub-count 0
         ring-count 0
         total 1]
    (cond (= ring-count 500) total
          (= sub-count 3) (recur (+ increment 2) ;; go to next ring
                                 (+ current increment 2)
                                 0
                                 (inc ring-count)
                                 (+ total current))
          :else (recur increment ;; go to next corner in current ring
                       (+ current increment)
                       (inc sub-count)
                       ring-count
                       (+ total current)))))


(time (problem-28))
;; "Elapsed time: 10.955234 msecs"
;; 669171001