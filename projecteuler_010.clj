;; Author: Christopher Olsen

;; Project Euler Problem 10
;;
;; The Summation Of Primes:
;; The sum of the primes below 10 is 2 + 3 + 5 + 7 = 17.
;;
;; Find the sum of all the primes below two million.


;; The Plan
;;
;; -Use the prime? function from Problem 7 instead of making a seive
;; -for the range 1-2,000,000, filter by prime? and use (reduce + ) to keep
;;  a running sum

;; from Problem 7
(defn prime? [number]
  ;; with Math/sqrt instead of rough-sqrt
  (cond (= number 1) false
        (= number 2) true
        (even? number) false ;; because of this we can iterate through the odds
        :else
        (do (def maximum (Math/sqrt number))
            (loop [divisor 3]
              (cond (> divisor maximum) true
                    (zero? (mod number divisor)) false
                    :else (recur (+ divisor 2)))))))

(defn prob10
  ([]
     (reduce + (filter prime? (range 1 2000000))))  
  ([max]
     (reduce + (filter prime? (range 1 max)))))
  
;; here we run through just to get a look at the time growth, this is getting
;; into the range where a prime-seive may prove it's usefulness

(time (prob10 10))
;; "Elapsed time: 1.33774 msecs"
;; 17

(time (prob10 100))
;; "Elapsed time: 9.8655 msecs"
;; 1060

(time (prob10 1000))
;; "Elapsed time: 9.88743 msecs"
;; 76127

(time (prob10 10000))
;; "Elapsed time: 53.529084 msecs"
;; 5736396

(time (prob10 100000))
;; "Elapsed time: 514.039879 msecs"
;; 454396537

(time (prob10 1000000))
;; "Elapsed time: 12032.868146 msecs"
;; 37550402023

(time (prob10 2000000))
;; "Elapsed time: 31154.428647 msecs"
;; 142913828922





;; or how about a prime seive?


;; this is a rare occasion of mutating a data structure in place
(defn sift [upper-limit]
  (let [s (atom (vec (range 0 (inc upper-limit))))]
    (swap! s assoc 1 0) ;; 1 is not prime
    (loop [index 2]
      ;;(println "outer loop, index:" index)
      (cond (> index upper-limit)
            @s
            (= (@s index) 0)
            (recur (inc index)) ;; if index value is 0, move to next index
            :else
            (do (loop [step (* 2 index)] ;; set all multiples of index to 0
                  ;;(println "inner loop, step:" step)
                  (cond (> step upper-limit)
                        '()
                        :else
                        (do (swap! s assoc step 0)
                            (recur (+ step index))))) ;; inner loop/recur
                (recur (inc index))))))) ;; outer loop/recur

(defn problem10_b [limit]
  (reduce + (sift limit)))

(time (problem10_b 2000000))

;; "Elapsed time: 24562.689368 msecs"
;; 142913828922

;; this is much slower (about 5x) than I expected, not sure why...adding an if
;; statement before the swap! only makes it slower...

;; maybe without the expense of atoms and swapping?


(defn sift_b [upper-limit]
  (let [s (vec (range 0 (inc upper-limit)))]
    (loop [seive (assoc s 1 0) ;; 1 is not prime
           index 2]
      ;;(println "outer loop, index:" index)
      (cond (> index upper-limit)
            seive
            (= (seive index) 0)
            (recur seive (inc index)) ;; if index value is 0, move to next index
            :else
            (recur (loop [seive2 seive ;; outer loop/recur
                          step (* 2 index)] ;; set all multiples of index to 0
                     ;;(println "inner loop, step:" step)
                     (cond (> step upper-limit)
                           seive2
                           :else
                           (recur (assoc seive2 step 0)
                                  (+ step index)))) ;; inner loop/recur
                   (inc index))))))

(defn problem10_c [limit]
  (reduce + (sift_b limit)))

(time (problem10_c 2000000))

;; "Elapsed time: 27799.237893 msecs"
;; 142913828922

;; so atoms/swap! aren't what's slowing this down....
