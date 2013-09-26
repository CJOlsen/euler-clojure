;; Author: Christopher Olsen

;; Project Euler Problem 4
;;
;; Problem Statement:
;; A palindromic number reads the same both ways. The largest palindrome made 
;; from the product of two 2-digit numbers is 9009 = 91 × 99.
;;
;; Find the largest palindrome made from the product of two 3-digit numbers.


;; The Plan
;; steps:
;;  - enumerate possibilities
;;  - filter by palindrome?
;;  - reduce with max

(defn make-products []
  (def p-list (transient '[])) 
  (loop [a 1]
    (if (> a 999)
      (persistent! p-list)
      (do (loop [b 1]
            (if (> b a) ;; (> b 1000) will cause duplicates ie 2x5 & 5x2
              '()
              (do (conj! p-list (* a b))
                  (recur (inc b)))))
          (recur (inc a))))))

(defn palindrome? [the-number]
  (= (str the-number) (clojure.string/reverse (str the-number))))

(time (reduce max (filter palindrome? (make-products))))
;; user=> "Elapsed time: 989.941319 msecs"
;; 906609
