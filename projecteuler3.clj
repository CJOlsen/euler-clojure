;; Author: Christopher Olsen

;; Project Euler Problem 3
;;
;; Problem Statement:
;; The prime factors of 13195 are 5, 7, 13 and 29.
;;
;; What is the largest prime factor of the number 600851475143 ?


;; The Plan
;;
;; It turns out there is a very elegant prime factor finding algorithm that fits
;; this problem well.  It starts at two and works its way upward looking for a 
;; factor, when one is found it's guaranteed to be prime because otherwise one
;; of its factors would have already been found.  When a factor is found it's 
;; saved for later and the search is resumed but now the number becomes the 
;; number divided by the factor that was just found (which is why it was 
;; guaranteed to be prime).
;; 
;; For example, if a number has a prime factor of 17, that number divided by 
;; 17 will be a multiple of every other prime factor.  We also know that there 
;; won't be any prime factors above x where (17 * x = number) because that 
;; would require another factor below 17 which would have already been found 
;; if it existed.
;;
;; (It took me a minute or ten to wrap my head around the algorithm, I found it
;; after spending forever trying to get a prime-seive to work without
;; overflowing the memory.)
;;
;; TL;DR
;; We find the prime factors using the super-fast algorithm, then find the 
;; biggest one by using reduce with max over the list.
;;
;; =>(factors-from 2 (* 17 17 17 17))
;; =>(17 17 17 17)
;;
;; =>(factors-from 2 (* 1 2 3 4 5 6 7 8))
;; =>(2 2 2 2 2 2 2 3 3 5 7)


(defn factors-from [lower number]
  (cond (= number 1) []
        (= (mod number lower) 0) (cons lower (factors-from lower
                                                           (/ number lower)))
        :else (recur (inc lower) number)))

(time (reduce max (factors-from 2 600851475143)))
;; "Elapsed time: 34.39362 msecs"
;; 6857
