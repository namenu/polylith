(ns polylith.clj.core.path-finder.interface
  (:require [polylith.clj.core.path-finder.paths :as paths]))

(defn paths [ws-dir environments profile-to-settings]
  (paths/paths ws-dir environments profile-to-settings))
