;; Author: Christopher Olsen

;; Project Euler Problem 15
;;
;; Lattice Paths
;;
;; Starting in the top left corner of a 2×2 grid, and only being able to move 
;; to the right and down, there are exactly 6 routes to the bottom right corner.
;;
;; How many such routes are there through a 20×20 grid?


;; The Plan
;;
;; First of all, if you're reading this because you're stumped and are looking
;; for ideas - get out the pencil and paper and start messing about if you
;; haven't already.  There's a pattern (it's worth the trouble to find it if
;; you can!)
;;
;; So stop reading!
;;
;; 
;; Ok, drawing a 3x3 grid and labeling how many paths led to each node (this
;; is after all the problem) a familiar pattern popped out - Pascal's Triangle
;; http://en.wikipedia.org/wiki/Pascal%27s_triangle
;;
;; So the plan is make a function that iterates rows of pascal's triangle, 
;; Our answer will be the max value of the 41st row!  (It's also the middle
;; value but since we're only looking for the max value of 41 options....)
;;
;; It helps to look at the triangle like this:
;; 1
;; 1 1
;; 1 2 1
;; 1 3 3 1
;; 1 4 6 4 1
;; 1 5 10....


(defn next-pascal
  [sequence]
  (cons 1 (map #(apply + %) (partition 2 1 [0] sequence))))

(defn pascal-row
  [number]
  (let [p (iterate next-pascal '(1))]
    (last (take number p))))

(defn prob-15
  []
  (apply max (pascal-row 41)))

(time (prob-15))
;; "Elapsed time: 2.344781 msecs"
;; 137846528820


;; or written differently
(defn problem-15
  []
  (-> '(1)                          ;; seed with the first row
      (#(iterate next-pascal %))    ;; make lazy infinite sequence of rows
      (#(last (take 41 %)))         ;; get the 41st row
      (#(apply max %))))            ;; get the max of that row

(time (problem-15))
;; "Elapsed time: 4.524737 msecs"
;; 137846528820
