(ns ^:no-doc polylith.clj.core.workspace-clj.bases-from-disk
  (:require [polylith.clj.core.common.interface :as common]
            [polylith.clj.core.common.interface.config :as config]
            [polylith.clj.core.lib.interface :as lib]
            [polylith.clj.core.util.interface :as util]
            [polylith.clj.core.workspace-clj.brick-dirs :as brick-dirs]
            [polylith.clj.core.workspace-clj.brick-paths :as brick-paths]
            [polylith.clj.core.workspace-clj.namespaces-from-disk :as ns-from-disk]
            [polylith.clj.core.workspace-clj.non-top-namespace :as non-top-ns]))

(defn read-base [ws-dir ws-type user-home top-namespace ns-to-lib top-src-dir interface-ns brick->settings base-deps-config]
  (let [config (:deps base-deps-config)
        base-name (:name base-deps-config)
        base-dir (str ws-dir "/bases/" base-name)
        files-to-ignore (get-in brick->settings [base-name :ignore-files])
        base-src-dirs (brick-dirs/top-src-dirs base-dir top-src-dir config)
        base-test-dirs (brick-dirs/top-test-dirs base-dir top-src-dir config)
        suffixed-top-ns (common/suffix-ns-with-dot top-namespace)
        namespaces (ns-from-disk/namespaces-from-disk ws-dir base-src-dirs base-test-dirs suffixed-top-ns interface-ns files-to-ignore)
        entity-root-path (str "bases/" base-name)
        lib-deps (lib/brick-lib-deps ws-dir ws-type config top-namespace ns-to-lib namespaces entity-root-path user-home)
        source-paths (config/source-paths config)
        non-top-namespaces (non-top-ns/non-top-namespaces "base" base-name base-dir top-src-dir source-paths)]
    (util/ordered-map :name base-name
                      :type "base"
                      :maven-repos (:mvn/repos config)
                      :paths (brick-paths/source-paths base-dir config)
                      :namespaces namespaces
                      :non-top-namespaces non-top-namespaces
                      :lib-deps lib-deps)))

(defn read-bases
  [ws-dir ws-type user-home top-namespace ns-to-lib top-src-dir interface-ns base-configs brick->settings]
  (vec (sort-by :name (map #(read-base ws-dir ws-type user-home top-namespace ns-to-lib top-src-dir interface-ns brick->settings %)
                           base-configs))))
