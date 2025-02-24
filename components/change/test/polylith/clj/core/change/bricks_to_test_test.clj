(ns polylith.clj.core.change.bricks-to-test-test
  (:require [clojure.test :refer :all]
            [polylith.clj.core.change.bricks-to-test :as to-test]
            [polylith.clj.core.change.test-data :as data])
  (:refer-clojure :exclude [test]))

(defn test [{:keys [changed-projects
                    settings
                    changed-components
                    changed-bases
                    project-to-indirect-changes
                    selected-bricks
                    selected-projects
                    is-dev-user-input
                    is-run-all-brick-tests]}]
  (to-test/project-to-bricks-to-test changed-projects
                                     data/projects
                                     settings
                                     changed-components
                                     changed-bases
                                     project-to-indirect-changes
                                     selected-bricks
                                     selected-projects
                                     is-dev-user-input
                                     is-run-all-brick-tests))

;; The development project is only included in the tests if we pass in :dev,
;; or if we include it with project:dev.

(deftest project-to-bricks-to-test--with-one-changed-component--returns-bricks-to-test-for-changed-projects
  (is (= (test {:changed-projects []
                :settings {}
                :changed-components ["article"]
                :changed-bases []
                :project-to-indirect-changes {}
                :selected-bricks nil
                :selected-projects #{}
                :is-dev-user-input false
                :is-run-all-brick-tests false})
         {"core" ["article"]
          "development" []})))

(deftest project-to-bricks-to-test--with-one-changed-component-that-is-excluded---returns-bricks-to-test-for-changed-projects
  (is (= (test {:changed-projects []
                :settings {:projects {"core" {:test {:include []}}}}
                :changed-components ["article"]
                :changed-bases []
                :project-to-indirect-changes {}
                :selected-bricks nil
                :selected-projects #{}
                :is-dev-user-input false
                :is-run-all-brick-tests false})
         {"core" []
          "development" []})))

(deftest project-to-bricks-to-test--with-run-all-selected--returns-all-bricks
  (is (= (test {:changed-projects []
                :settings {}
                :changed-components ["article"]
                :changed-bases []
                :project-to-indirect-changes {}
                :selected-bricks nil
                :selected-projects #{}
                :is-dev-user-input false
                :is-run-all-brick-tests true})
         {"core" ["article"
                  "comment"
                  "rest-api"
                  "tag"
                  "user"]
          "development" []})))

(deftest project-to-bricks-to-test--with-run-all-brick-tests-and-development-included--returns-all-bricks
  (is (= (test {:changed-projects []
                :settings {}
                :changed-components ["article"]
                :changed-bases []
                :project-to-indirect-changes {}
                :selected-bricks nil
                :selected-projects #{}
                :is-dev-user-input true
                :is-run-all-brick-tests true})
         {"core" ["article"
                  "comment"
                  "rest-api"
                  "tag"
                  "user"]
          "development" ["article"
                         "comment"
                         "profile"
                         "rest-api"
                         "tag"
                         "user"]})))

(deftest project-to-bricks-to-test--with-run-all-brick-tests-and-development-selected--returns-all-bricks-for-development
  (is (= (test {:changed-projects []
                :settings {}
                :changed-components ["article"]
                :changed-bases []
                :project-to-indirect-changes {}
                :selected-bricks nil
                :selected-projects #{"dev"}
                :is-dev-user-input false
                :is-run-all-brick-tests true})
         {"core" []
          "development" ["article"
                         "comment"
                         "profile"
                         "rest-api"
                         "tag"
                         "user"]})))

(deftest project-to-bricks-to-test--include-two-bricks--returns-the-two-bricks
  (is (= (test {:changed-projects []
                :settings {:projects {"core" {:test {:include ["tag" "user"]}}}}
                :changed-components ["article" "comment" "rest-api" "tag" "user"]
                :changed-bases []
                :project-to-indirect-changes {}
                :selected-bricks nil
                :selected-projects #{}
                :is-dev-user-input false
                :is-run-all-brick-tests false})
         {"core" ["tag"
                  "user"]
          "development" []})))

(deftest project-to-bricks-to-test--when-the-project-itself-has-changed--return-all-bricks-for-that-project
  (is (= (test {:changed-projects ["core"]
                :settings {}
                :changed-components ["article"]
                :changed-bases []
                :project-to-indirect-changes {}
                :selected-bricks nil
                :selected-projects #{}
                :is-dev-user-input false
                :is-run-all-brick-tests false})
         {"core" ["article"
                  "comment"
                  "rest-api"
                  "tag"
                  "user"]
          "development" []})))

(deftest project-to-bricks-to-test--with-two-changed-components-and-one-selected-brick--returns-selected-bricks-that-are-also-changed
  (is (= (test {:changed-projects []
                :settings {}
                :changed-components ["article" "user"]
                :changed-bases []
                :project-to-indirect-changes {}
                :selected-bricks ["user"]
                :selected-projects #{}
                :is-dev-user-input false
                :is-run-all-brick-tests false})
         {"core" ["user"]
          "development" []})))

(deftest project-to-bricks-to-test--with-no-changed-components-and-one-selected-brick--returns-no-bricks
  (is (= (test {:changed-projects []
                :settings {}
                :changed-components []
                :changed-bases []
                :project-to-indirect-changes {}
                :selected-bricks ["tag"]
                :selected-projects #{}
                :is-dev-user-input false
                :is-run-all-brick-tests false})
         {"core" []
          "development" []})))

(deftest project-to-bricks-to-test--with-no-changed-components-and-one-selected-brick-with-run-all-brick-tests--returns-selected-brick
  (is (= (test {:changed-projects []
                :settings {}
                :changed-components []
                :changed-bases []
                :project-to-indirect-changes {}
                :selected-bricks ["tag"]
                :selected-projects #{}
                :is-dev-user-input false
                :is-run-all-brick-tests true})
         {"core" ["tag"]
          "development" []})))
