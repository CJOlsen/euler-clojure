;; Author: Christopher Olsen

;; Project Euler Problem 14

;; Largest Collatz Sequence
;;
;; The following iterative sequence is defined for the set of positive integers:
;;
;; n → n/2 (n is even)
;; n → 3n + 1 (n is odd)
;;
;; Using the rule above and starting with 13, we generate the following 
;; sequence:
;; 13 → 40 → 20 → 10 → 5 → 16 → 8 → 4 → 2 → 1
;;
;; It can be seen that this sequence (starting at 13 and finishing at 1) 
;; contains 10 terms. Although it has not been proved yet (Collatz Problem), 
;; it is thought that all starting numbers finish at 1.
;;
;; Which starting number, under one million, produces the longest chain?
;;
;; NOTE: Once the chain starts the terms are allowed to go above one million.


;; The Plan
;;
;; - LISP's were made for this type of problem
;; 1) state the Collatz Sequence as a function that counts its steps
;; 2) find max: (max-key #(collatz %) (range 1 1000001)) 

(defn collatz
  [number]
  (defn helper
    [n c] ;; c is count
    (cond (= n 1) c
          (even? n) (recur (/ n 2) (inc c))
          :else (recur (inc (* n 3)) (inc c))))
  (helper number 1))

(defn problem-14 
  [limit]
  (apply max-key collatz (range 1 (inc limit))))

(time (problem-14 1000000))
;; "Elapsed time: 243028.83824 msecs"
;; 837799

;; Wow, 4 minutes.  That's a little on the high side.    



(defn collatz-2
  ;; a non-tail recursive version that lends itself to memoization
  [number]
  (cond (= number 1) 1
        (even? number) (+ 1 (collatz-2 (/ number 2)))
        :else (+ 1 (collatz-2 (inc (* number 3))))))

(def collatz-memo (memoize collatz-2))

(defn problem-14-memo
  [limit]
  (apply max-key collatz-memo (range 1 (inc limit))))

(time (problem-14-memo 1000000))
;; "Elapsed time: 65619.664346 msecs"
;; 837799

(/ 65619.66 243028.83)
;; 0.2700...

;; memoization makes it run in a roughly a fourth of the time
