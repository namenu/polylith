(ns ^:no-doc polylith.clj.core.validator.datashape.dispatcher
  (:require [polylith.clj.core.validator.datashape.toolsdeps1 :as toolsdeps1]
            [polylith.clj.core.validator.datashape.toolsdeps2 :as toolsdeps2]))

(defn throw-unknown-type [ws-type]
  (throw (Exception. (str "Unknown ws-type: " ws-type))))

(defn validate-project-dev-config [ws-type config filename]
  (case ws-type
    :toolsdeps1 (toolsdeps1/validate-dev-config config filename)
    :toolsdeps2 (toolsdeps2/validate-project-dev-config config filename)
    (throw-unknown-type ws-type)))

(defn validate-project-deployable-config [ws-type config filename]
  (case ws-type
    :toolsdeps1 (toolsdeps1/validate-project-deployable-config config filename)
    :toolsdeps2 (toolsdeps2/validate-project-deployable-config config filename)
    (throw-unknown-type ws-type)))
