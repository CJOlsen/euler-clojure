;; Author: Christopher Olsen

;; Project Euler Problem 17
;;
;; Number Letter Counts
;;
;; 

;; If the numbers 1 to 5 are written out in words: one, two, three, four, 
;; five, then there are 3 + 3 + 5 + 4 + 4 = 19 letters used in total.
;;
;; If all the numbers from 1 to 1000 (one thousand) inclusive were written 
;; out in words, how many letters would be used?
;;
;; NOTE: Do not count spaces or hyphens. For example, 342 (three hundred and 
;; forty-two) contains 23 letters and 115 (one hundred and fifteen) contains 
;; 20 letters. The use of "and" when writing out numbers is in compliance with 
;; British usage.


;; The Plan
;;
;; -Make a hash map of major number denominations to their string 
;;  representations
;; -Convert those to letter counts
;; -write function for decomposition of a number into it's hundreds, tens, ones
;; -write function that takes a decomposition, and returns the character count
;;  of what a string would be (but looking it up in num-strings instead of 
;;  building a string)
;; -(this should work up to 9,999 if the 'and' is accounted for after
;;   the thousands the same way it is after the hundreds, but right now it
;;   messes up on numbers like 9,014 because it misses the 'and')

(def num-strings
  {1 "one" 2 "two" 3 "three" 4 "four" 5 "five" 6 "six" 7 "seven" 8 "eight" 9
   "nine" 10 "ten" 11 "eleven" 12 "twelve" 13 "thirteen" 14 "fourteen" 15 
   "fifteen" 16 "sixteen" 17 "seventeen" 18 "eighteen" 19 "nineteen" 20 "twenty"
   30 "thirty" 40 "forty" 50 "fifty" 60 "sixty" 70 "seventy" 80 "eighty" 90
   "ninety" 100 "hundred" 1000 "thousand"})

(def num-counts
  ;; counts the letters of each value in num-strings and builds a new hashmap
  ;; of them
  (do (def dummy-map {})
      (-> (map (fn [x] (assoc dummy-map x (count (num-strings x))))
               (keys num-strings))
          (#(apply merge %)))))

(defn decomposition
  [number]
  (hash-map
   "ones" (rem number 10)
   "tens" (quot (rem number 100) 10)
   "hundreds" (quot (rem number 1000) 100)
   "thousands" (quot number 1000)))

(defn recomposition
  [decomp]
  (+ (* (decomp "thousands") 1000)
     (* (decomp "hundreds") 100)
     (* (decomp "tens") 10)
     (* (decomp "ones") 1)))

(defn count-string
  [decomp]
  (defn helper [decomp total]
    ;; this very much violates the DRY principle, and maybe should be rewritten
    ;; the basic idea of this loop is that once the contribution from the 
    ;; "hundreds" have been added to the total it goes back to the top with
    ;; the "hundreds" removed and deals with the "tens", etc.
    (cond (> (decomp "thousands") 0)
          ;(println "count 1000")
          (helper (assoc decomp "thousands" 0)
                  (+ total
                     (num-counts (decomp "thousands"))
                     (num-counts 1000)))
          (> (decomp "hundreds") 0)
          (helper (assoc decomp "hundreds" 0) 
                  (+ total
                     (num-counts (decomp "hundreds"))
                     (num-counts 100)
                     (if (or (> (decomp "tens") 0)
                             (> (decomp "ones") 0))
                       3 0)))
          (> (decomp "tens") 1)
          (helper (assoc decomp "tens" 0) 
                  (+ total
                     (num-counts (* (decomp "tens") 10))))
          (= (decomp "tens") 1)
          (helper (assoc decomp "tens" 0 "ones" 0)
                  (+ total
                     ;(num-counts (+ (decomp "ones") 10))
                     (num-counts (recomposition decomp))))
          (> (decomp "ones") 0)
          (helper (assoc decomp "ones" 0) (+ total
                                             (num-counts (decomp "ones"))))
          :else total))
  (helper decomp 0))

(defn prob-17
  []
  (reduce + (map #(count-string (decomposition %)) (range 1 1001))))

(time (prob-17))
;; "Elapsed time: 25.804398 msecs"
;; 21124
