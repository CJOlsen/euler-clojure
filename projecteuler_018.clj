;; Author: Christopher Olsen

;; Project Euler Problem 18

;; Maximum path sum I
;;
;; By starting at the top of the triangle below and moving to adjacent numbers 
;; on the row below, the maximum total from top to bottom is 23.
;;
;; 3
;; 7 4
;; 2 4 6
;; 8 5 9 3
;;
;; That is, 3 + 7 + 4 + 9 = 23.
;;
;; Find the maximum total from top to bottom of the triangle below:
;;
;; 75
;; 95 64
;; 17 47 82
;; 18 35 87 10
;; 20 04 82 47 65
;; 19 01 23 75 03 34
;; 88 02 77 73 07 63 67
;; 99 65 04 28 06 16 70 92
;; 41 41 26 56 83 40 80 70 33
;; 41 48 72 33 47 32 37 16 94 29
;; 53 71 44 65 25 43 91 52 97 51 14
;; 70 11 33 28 77 73 17 78 39 68 17 57
;; 91 71 52 38 17 14 91 43 58 50 27 29 48
;; 63 66 04 68 89 53 67 30 73 16 69 87 40 31
;; 04 62 98 27 23 09 70 98 73 93 38 53 60 04 23
;;
;; NOTE: As there are only 16384 routes, it is possible to solve this problem 
;; by trying every route. However, Problem 67, is the same challenge with a 
;; triangle containing one-hundred rows; it cannot be solved by brute force, 
;; and requires a clever method! ;o)


;; The Plan
;;
;; -create a function to be used as a reduce argument that will merge two
;;  rows of the triangle
;; -then it's just (reduce magic-func triangle)
;; -magic-func will take two rows, the larger first.  It will create a new
;;  row of the same degree as the shorter row.  Each element of the new
;;  row will be the sum of the same index element of the short row and the max
;;  of the same index and the same index plus one of the longer row.
;;  Partition will be used with (reduce max ...) to create a row which can be
;;  added 1-to-1 to the shorter row to accomplish this.

(def triangle
  (-> 
"75
95 64
17 47 82
18 35 87 10
20 04 82 47 65
19 01 23 75 03 34
88 02 77 73 07 63 67
99 65 04 28 06 16 70 92
41 41 26 56 83 40 80 70 33
41 48 72 33 47 32 37 16 94 29
53 71 44 65 25 43 91 52 97 51 14
70 11 33 28 77 73 17 78 39 68 17 57
91 71 52 38 17 14 91 43 58 50 27 29 48
63 66 04 68 89 53 67 30 73 16 69 87 40 31
04 62 98 27 23 09 70 98 73 93 38 53 60 04 23"
      (#((fn [x] (clojure.string/split x #"\n")) %))
      (#(map (partial re-seq #"[1-9]+[0-9]*|0{2}") %))
      (#(map (partial map read-string) %))
      (#(vec (map vec %)))
      reverse)) ;; flip it upside downs so we're working on the head

(defn magic-func
  [longer shorter]
  (map + shorter
         (map #(apply max %) (partition 2 1 [1] longer))))

(defn problem-18
  []
  (first (reduce magic-func triangle)))

(time (problem-18))
;; "Elapsed time: 2.082457 msecs"
;; 1074




;; or almost all at once (except magic-func which is too funky to party with
;; the others

(def raw-in
"75
95 64
17 47 82
18 35 87 10
20 04 82 47 65
19 01 23 75 03 34
88 02 77 73 07 63 67
99 65 04 28 06 16 70 92
41 41 26 56 83 40 80 70 33
41 48 72 33 47 32 37 16 94 29
53 71 44 65 25 43 91 52 97 51 14
70 11 33 28 77 73 17 78 39 68 17 57
91 71 52 38 17 14 91 43 58 50 27 29 48
63 66 04 68 89 53 67 30 73 16 69 87 40 31
04 62 98 27 23 09 70 98 73 93 38 53 60 04 23")


(defn prob-18
  [in-string]
  (-> in-string
      (#((fn [x] (clojure.string/split x #"\n")) %))   ;; split input lines
      (#(map (partial re-seq #"[1-9]+[0-9]*|0{2}") %)) ;; strip leading zeros
      (#(map (partial map read-string) %))             ;; cast to integers
      reverse                                          ;; longest row first
      (#(reduce magic-func %))                         ;; see notes above
      first))

(time (prob-18 raw-in))
;; "Elapsed time: 6.680807 msecs"
;; 1074
