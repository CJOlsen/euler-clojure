;; Author: Christopher Olsen

;; Project Euler Problem 19
;;
;; Counting Sundays
;;
;; You are given the following information, but you may prefer to do some 
;; research for yourself.
;;
;;     1 Jan 1900 was a Monday.
;;     Thirty days has September,
;;     April, June and November.
;;     All the rest have thirty-one,
;;     Saving February alone,
;;     Which has twenty-eight, rain or shine.
;;     And on leap years, twenty-nine.
;;     A leap year occurs on any year evenly divisible by 4, but not on a 
;; century unless it is divisible by 400.
;;
;; How many Sundays fell on the first of the month during the twentieth 
;; century (1 Jan 1901 to 31 Dec 2000)?

;; The Plan
;;
;; - Make a list of Sundays from 1 Jan 1901 (i.e. 0 0 0 0 0 0 1 0 0 0 0 0 0 1)
;; - Make a list of first days from the same (i.e. 1 0 0 ..... 1 0 0 .....)
;; - count indexes where each list has a 1


(defn days-per-month
  [month year]
  (+ ({1 31 2 28 3 31 4 30 5 31 6 30 7 31 8 31 9 30 10 31 11 30 12 31} month)
     (cond (zero? (rem year 400)) 1
           (zero? (rem year 100)) 0
           (zero? (rem year 4)) 1
           :else 0)))

(defn days-per-year
  [year]
  (+ 365 (cond (zero? (rem year 400)) 1
               (zero? (rem year 100)) 0
               (zero? (rem year 4)) 1
               :else 0)))
(zero? (rem 1905 400))

(memoize days-per-month)
(memoize days-per-year)

(def leap-years
  (filter (fn [x] (cond (zero? (rem x 100)) (if (zero? (rem x 4))
                                              true false)
                        (zero? (rem x 4)) true
                        :else false))
          (range 1901 2001)))

leap-years
                               
                             
                             
                                   

(def days-for-century
  (reduce + (map #(days-per-year %) (range 1901 2001))))
;; days-for-century = 36525



(def sundays-list
  (map #(if (zero? (rem % 7)) 1 0) (range 1 36526)))

(def first-days-list
  [year
  (do
    (loop [year year
           month 1
           current-list '()]
      (cond (> year 1901) current-list
            (= month 12) (recur (inc year) 1 (concat current-list
                                                     '(1)
                                                     (repeat 30 0)))
            :else (recur year
                         (inc month)
                         (concat current-list
                                 '(1)
                                 (repeat (dec (days-per-month month year))
                                         0)))))
    (helper 1901 1 '())))
(take 1000 first-days-list)

(concat (map 

(def a '(1 2 3))

(concat a '(5 6 7) (repeat 5 555))

(days-per-month 1 1901)
(concat (repeat 30 0) '(1) (repeat 31 0))

(loop [sundays sundays-list
       firstdays first-days-list
       count 0]
  (cond (empty? sundays) count
        (and (= 1 (first sundays))
             (= 1 (first firstdays))) (recur (rest sundays)
                                             (rest firstdays)
                                             (inc count))
             :else (recur (rest sundays) (rest firstdays) count)))
             
(take 50 sundays-list)
(count (take 50 first-days-list))
  



;; (def _days-per-month
;;   {1 31 3 31 4 30 5 31 6 30 7 31 8 31 9 30 10 31 11 30 12 31})

;; (def regular-year-days
;;   (merge _days-per-month {2 28}))

;; (def leap-year-days
;;  (merge _days-per-month {2 29})




(days-of-week '(0 1 2 3 4 5 6))

(def month-codes {"January" 6 "February" 2 "March" 2 "April" 5 "May" 0
                  "June" 3 "July" 5 "August" 1 "September" 4 "October" 6
                  "November" 2 "December" 4}