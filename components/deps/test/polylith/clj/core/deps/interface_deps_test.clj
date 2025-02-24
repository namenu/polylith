(ns polylith.clj.core.deps.interface-deps-test
  (:require [clojure.test :refer :all]
            [polylith.clj.core.deps.interface-deps :as deps]))

(deftest dependency--without-top-namespace--returns-dependencies
  (is (= (deps/dependency "" "common" "ns" #{"spec" "cmd" "file" "common"}
                          "file.interface")
         {:namespace "ns"
          :depends-on-interface "file"
          :depends-on-ns "interface"})))

(deftest dependency--with-top-namespace--returns-dependencies
  (is (= (deps/dependency "polylith." "common" "ns" #{"spec" "cmd" "file" "common"}
                          "polylith.file.interface")
         {:namespace "ns"
          :depends-on-interface "file"
          :depends-on-ns "interface"})))

(deftest interface-ns-import-deps--with-top-namespace--returns-dependencies
  (is (= (deps/interface-ns-import-deps "polylith." "common" #{"spec" "cmd" "file" "invoice"}
                                        '{:name "core.clj"
                                          :imports ["clojure.core"
                                                    "polylith.user.interface"
                                                    "polylith.cmd.core"
                                                    "polylith.invoice.core"]})
         [{:namespace "core.clj"
           :depends-on-interface "cmd"
           :depends-on-ns "core"}
          {:namespace "core.clj"
           :depends-on-interface "invoice"
           :depends-on-ns "core"}])))

(deftest interface-ns-deps--with-top-namespace--returns-dependencies
  (is (= (deps/interface-ns-deps "polylith." "common" #{"spec" "cmd" "user" "invoice"}
                                 '[{:name "core.clj"
                                    :imports ["clojure.string"
                                              "polylith.file.interface"]}
                                   {:name "abc.clj"
                                    :imports ["clojure.core"
                                              "polylith.user.interface"
                                              "polylith.cmd.core"
                                              "polylith.invoice.core"]}])
         [{:namespace "abc.clj"
           :depends-on-interface "user"
           :depends-on-ns "interface"}
          {:namespace "abc.clj"
           :depends-on-interface "cmd"
           :depends-on-ns "core"}
          {:namespace "abc.clj"
           :depends-on-interface "invoice"
           :depends-on-ns "core"}])))
