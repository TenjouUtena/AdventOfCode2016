(ns aoc2019.17
  (:require [aoc2019.machine :as m]
            [aoc2019.grid :as g]
            [clojure.string :as s]))


(def input (slurp "resources/17.txt"))

(def machine (m/create-machine-spec input))

(defn loadmap [mac]
  (reduce-kv (fn [grid y row]
               (reduce-kv (fn [gr x tile]
                            (g/set-node gr {:tile tile} [x y]))
                          grid
                          (vec row)))
             g/base-grid
             (s/split (apply str (map char (:outputstream (m/run-machine machine)))) #"\n")
    ))

(defn scaffold? [tile]
  (= (:tile tile) \#))

(defn find-junctions [gr]
  (filter (fn [tile]
            (every? scaffold? (map #(g/get-node gr %)
                                   (g/adjacent-locs gr (first tile)))))
          (filter #(scaffold? (second %)) (:locations gr))))


(defn align-parameters [locs]
  (reduce + (map #(apply * %) (map first locs))))
