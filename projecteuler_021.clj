;; Author: Christopher Olsen

;; Project Euler Problem 21
;;
;; Amicable Numbers
;;
;; Let d(n) be defined as the sum of proper divisors of n (numbers less 
;; than n which divide evenly into n).
;; If d(a) = b and d(b) = a, where a ≠ b, then a and b are an amicable 
;; pair and each of a and b are called amicable numbers.
;;
;; For example, the proper divisors of 220 are 1, 2, 4, 5, 10, 11, 20, 22, 
;; 44, 55 and 110; therefore d(220) = 284. The proper divisors of 284 are 1, 
;; 2, 4, 71 and 142; so d(284) = 220.
;;
;; Evaluate the sum of all the amicable numbers under 10000.


;; The Plan
;;
;; - write factor function, and then a sum of factors function
;; - map that function across (range 1 10001)
;; - use that to write a "amicable" function that checks if a number is part of
;;   an amicable pair
;; - then sum those pairs

(defn factors 
  [number]
  (loop [fact-list '(1)
         current 2]
    (cond (> current (/ number 2)) fact-list 
          (zero? (rem number current)) (recur (cons current fact-list)
                                              (inc current))
          :else (recur fact-list (inc current)))))

(defn sum-facts
  [number]
  (reduce + (factors number)))

(defn amicable
  ;; takes a number, if it has an amicable pair both are returned in a 2-vector
  ;; otherwise an empty vector is returned
  [number]
  (let [a number
        b (sum-facts number)]
    (if (and (not (= a b))
             (= a (sum-facts b)))
      [a b]
      [])))

(defn problem-21
  []
  (-> (range 0 10001)
          (#(map amicable %))
          (#(reduce + (distinct (flatten %))))))

(time (problem-21))
;; "Elapsed time: 34159.650331 msecs"
;; 31626

(defn problem-21-ver2
  []
  (-> (range 0 10001)
      (#(map amicable %))
      (#(reduce + (flatten %)))
      (#(/ % 2))))

(time (problem-21-ver2))
;; "Elapsed time: 32116.476824 msecs"
;; 31626