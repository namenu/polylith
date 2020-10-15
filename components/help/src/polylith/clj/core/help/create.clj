(ns polylith.clj.core.help.create
  (:require [polylith.clj.core.help.shared :as s]
            [polylith.clj.core.help.create-component :as component]
            [polylith.clj.core.help.create-base :as base]
            [polylith.clj.core.help.create-project :as project]
            [polylith.clj.core.help.create-workspace :as workspace]))

(defn help-text [cm]
  (str "  Creates a component, base, project or workspace.\n"
       "\n"
       "  poly create " (s/key "TYPE" cm) " [" (s/key "ARGS" cm) "]\n"
       "    " (s/key "TYPE" cm) " = " (s/key "c" cm) " -> Creates a component.\n"
       "           " (s/key "b" cm) " -> Creates a base.\n"
       "           " (s/key "p" cm) " -> Creates a project.\n"
       "           " (s/key "w" cm) " -> Creates a workspace.\n"
       "\n"
       "    " (s/key "ARGS" cm) " = Varies depending on " (s/key "TYPE" cm) ". To get help for a specific " (s/key "TYPE" cm) ", type:\n"
       "             poly help create " (s/key "TYPE" cm) "\n"
       "\n"
       "  Not only " (s/key "c" cm) ", " (s/key "b" cm) ", " (s/key "p" cm) " and " (s/key "w" cm) " can be used for " (s/key "TYPE" cm) " but also " (s/key "component" cm) ", " (s/key "base" cm) "\n"
       "  " (s/key "project" cm) " and " (s/key "workspace" cm) ".\n"
       "\n"
       "  Example:\n"
       "    poly create c name:user\n"
       "    poly create c name:admin interface:user\n"
       "    poly create b name:mybase\n"
       "    poly create p name:myproject\n"
       "    poly create w name:myws top-ns:com.my.company"))

(defn print-help [ent color-mode]
  (case ent
    "c" (component/print-help color-mode)
    "component" (component/print-help color-mode)
    "b" (base/print-help color-mode)
    "base" (base/print-help color-mode)
    "p" (project/print-help color-mode)
    "project" (project/print-help color-mode)
    "w" (workspace/print-help color-mode)
    "workspace" (workspace/print-help color-mode)
    (println (help-text color-mode))))
