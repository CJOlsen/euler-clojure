;; Author: Christopher Olsen

;; Project Euler Problem 8
;;
;; Problem Statement
;; Find the greatest product of five consecutive digits in the 1000-digit
;; number.
;;
;; 73167176531330624919225119674426574742355349194934
;; 96983520312774506326239578318016984801869478851843
;; 85861560789112949495459501737958331952853208805511
;; 12540698747158523863050715693290963295227443043557
;; 66896648950445244523161731856403098711121722383113
;; 62229893423380308135336276614282806444486645238749
;; 30358907296290491560440772390713810515859307960866
;; 70172427121883998797908792274921901699720888093776
;; 65727333001053367881220235421809751254540594752243
;; 52584907711670556013604839586446706324415722155397
;; 53697817977846174064955149290862569321978468622482
;; 83972241375657056057490261407972968652414535100474
;; 82166370484403199890008895243450658541227588666881
;; 16427171479924442928230863465674813919123162824586
;; 17866458359124566529476545682848912883142607690042
;; 24219022671055626321111109370544217506941658960408
;; 07198403850962455444362981230987879927244284909188
;; 84580156166097919133875499200524063689912560717606
;; 05886116467109405077541002256983155200055935729725
;; 71636269561882670428252483600823257530420752963450


;;
;; the easier method
;;

;; The Plan
;; - make vector of number digits
;; - make vector of product of each digit and the four following it
;; - find max of that vector using (reduce max ...)


(def n 7316717653133062491922511967442657474235534919493496983520312774506326239578318016984801869478851843858615607891129494954595017379583319528532088055111254069874715852386305071569329096329522744304355766896648950445244523161731856403098711121722383113622298934233803081353362766142828064444866452387493035890729629049156044077239071381051585930796086670172427121883998797908792274921901699720888093776657273330010533678812202354218097512545405947522435258490771167055601360483958644670632441572215539753697817977846174064955149290862569321978468622482839722413756570560574902614079729686524145351004748216637048440319989000889524345065854122758866688116427171479924442928230863465674813919123162824586178664583591245665294765456828489128831426076900422421902267105562632111110937054421750694165896040807198403850962455444362981230987879927244284909188845801561660979191338754992005240636899125607176060588611646710940507754100225698315520005593572972571636269561882670428252483600823257530420752963450)


;; number as string
(def nas (str n))

;; number as vector
(def nav (map read-string (rest (clojure.string/split nas #""))))

(defn slice-vector [a-vect low-index hi-index]
  ;; retrieves a slice of a vector including the lower index but not the high
  (loop [ret-vect '[]
         index low-index]
    (if (>= index hi-index)
      ret-vect
      (recur (conj ret-vect (nth a-vect index))
             (inc index)))))

(defn problem8 []
  (reduce max
    (loop [products '[]
           index 0]
      (cond (> index 995) products
            :else (recur (conj products
                               (reduce * ((fn [i] (slice-vector nav i (+ i 5)))
                                          index)))
                         (inc index))))))

(time (problem8))
;; "Elapsed time: 217.577389 msecs"
;; 40824




;; The Harder Plan (to do...)
;;
;; - write method to slice strings (and cast number to string)
;; - start with first five digits, get product with (reduce * ...)
;; - divide by first digit and multiply by the sixth to get the next product
;;   - compare them and keep the larger
;; - if the next digit is zero, then move forward past the zero and start again
;;   but keeping the current max product
;; - at the end of the list, the current max product is the winner


(def nas (str n)) ;; number as string

(defn slice [a-string low-index hi-index]
  ;; retrieves a slice of a string including the lower index but not the high
  ;; slice is zero indexed
  (loop [ret-str ""
         index low-index]
    (if (>= index hi-index)
      ret-str
      (recur (clojure.string/join [ret-str (str (nth a-string index))])
             (inc index)))))

(defn int-nth [a-string index]
  (int (nth a-string index)))

(defn find-5-nonzero [a-string index]
  ;; starting at index, find the index where the first 5 cosecutive non-zero
  ;; digits start, return a two-vector with the index and their product
  (loop [cur-index index
         sub-string ""]
    (cond (>= index (count a-string)) [(count a-string) 0]
          (= (count sub-string) 5) [(- index 6)
                                    (map * (map int (clojure.string/split
                                                     sub-string #"")))]
          (= (int-nth a-string cur-index) 0) (recur (inc index)
                                                    "")
          :else (recur (inc index)
                       (clojure.string/join [sub-string
                                             (str (nth a-string index))])))))

(find-5-nonzero "12345678" 2)

(defn prob8 [number]
  ;; doesn't work if there's a zero in the first five digits
  (loop [the-max 0
         index 0
         current-product (map * (map int (slice number 0 5)))]
    (cond (>= current-start 995) the-max
          (= current-product 0)
          (= (int-nth number (+ index 5)) 0) 
  