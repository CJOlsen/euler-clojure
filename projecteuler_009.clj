;; Author: Christopher Olsen

;; Project Euler Problem 9
;;
;; 
;; A Pythagorean triplet is a set of three natural numbers, a < b < c, for which,
;; a^2 + b^2 = c^2

;; For example, 3^2 + 4^2 = 9 + 16 = 25 = 5^2.

;; There exists exactly one Pythagorean triplet for which a + b + c = 1000.
;; Find the product abc.


;; The Plan
;;
;; Just loop through 'em and check a^2 + b^2 = c^2
;;  - a (1-995), b (a-996), c (b-997)
;;               b (a+1) -> (1000-a-(b+1))


;; a try using "for", could be optimized
(defn get-triplet []
  (for [a (range 1 995)
        b (range a (inc (quot (- 1000 a) 2))) ;; saves a little bit of time
        :when (let [c (- 1000 a b)]
                (and (> c 0)
                     (= (+ (* a a) (* b b))
                        (* c c))))]
    [a b (- 1000 a b)]))

(defn prob9 []
  (reduce * (first (get-triplet)))) 

(time (prob9))
;; "Elapsed time: 82.66547 msecs"
;; 31875000



;; and a semi-optimized nested-loop version
(defn get-the-3 []
  (loop [a 1]
    (if (> a 995)
      '()
      (let [b-max (inc (quot (- 1000 a) 2))
            z (loop [b (+ a 1)
                     c (- 1000 a b)]
               (cond (> b b-max) '()
                     (< c b) '()
                     (and (> c 0)
                          (= (+ (* a a) (* b b))
                             (* c c)))
                       [a b c]
                     :else (recur (inc b)
                                  (- c 1))))]
        (if (empty? z)
          (recur (inc a))
          z)))))

(defn probl-9 []
  (reduce * (get-the-3)))

(time (probl-9))
;; "Elapsed time: 15.891825 msecs"
;; 31875000
