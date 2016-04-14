
(ns ^{:doc "example natural language interpreter"
      :author "Kyle Richardson"}
    lecture2.core
  (:use [clojure.set])) 


;; set definitions -- evaluates sets directly rather than creating another variable

(def people-studying #{"john" "mary"})
(def people-sleeping #{"bill" "francis"}) 

(def study
  "either determines if an individual is studying, or returns a set"
  (fn
    ;; if there is an argument, see if it is in set
    ([x] (contains?
          people-studying x))
    ;; otherwise return the set
    ([] people-studying))) 

(def sleep
  "determine if an individual is sleeping or return a set"
  (fn
    ([x] (contains?
          people-sleeping x))
    ([] people-sleeping)))

;; negation



;; in clojure, (fn [x] body) is syntactically the same as (lambda (x) body) (in standard lisp) 
;; e.g (lambda x,y : x > 7)(10,3) (python) ==> ((fn [x y] (> x y)) 10 3)

; (def symbol body) associates or binds a symbol ``symbol'' with an anonymous function 

(def neg
  "returns an anonymous, the negation of ``fun''"
  (fn [fun]
    ;; definition of new anonymous function 
    (fn [& args]
      (not
       (apply fun args)))))

(def l-and
  "implementation of conjunction"
  (fn
    ;; written in curried form (takes one argument at a time) 
    ([x]
    (fn [y]
      (fn [f] (and (f x)
                   (f y)))))
    ;; written as a binary relation (takes two arguments 
    ([x y]
    (fn [f] (and (f x)
                 (f y))))))

(def l-or
  "implementation of conjunction"
  (fn
    ;; written in curried form (takes one argument at a time) 
    ([x]
    (fn [y]
      (fn [f] (or (f x)
                  (f y)))))
    ;; written as a binary relation (takes two arguments 
    ([x y]
    (fn [f] (or (f x)
                (f y))))))

(def l-every
  "implementation of ``every'' (as a generalized quantifier)"
  (fn [x,y]
    (let [overlap (count (clojure.set/intersection x y))] 
      (and (> overlap 0)
           (>= overlap (count x))))))

;; when in clojure shell, type (load-file "lecture2.clj")
;; then: (demo)

(def demo
  "show some examples using our interpreter" 
  (fn []
    ;; print out all examles

    (println
     (str
      ; example 1 
      (str (str "john is sleeping: ")
           (str (sleep "john") "\n"))
      
      ;example 2 
      (str (str "bill studies: ")
           (str (study "bill") "\n"))
      
      ;; example 3
      (str (str "bill does not sleep: ")
           (str ((neg study) "bill") "\n"))
      
      ;; example 4
      (str (str "john and mary study: ")
           ;; in curried form
           (str (((l-and "john") "mary") study) "\n"))
      
      ;; example 5
      (str (str "john or mary study: ")
           ;; in binary form 
           (str ((l-or "john" "mary") study) "\n")

      ;; example 6
      (str (str "john or mary do not sleep: ")
           ;; in binary form 
           (str ((l-or "john" "mary") (neg sleep)) "\n"))

      )))))

;; gets called when you do: clj lecture2.clj

(def main- 
  (demo))
