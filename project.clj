(defproject maria "0.1.0-SNAPSHOT"
  :description "A learning project."
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :min-lein-version "2.7.1"

  :dependencies [[org.clojure/clojure "1.9.0-alpha17"]
                 [org.clojure/clojurescript "1.9.671"]
                 [org.clojure/core.match "0.3.0-alpha4"]
                 [org.clojure/tools.reader "1.0.3"]
                 [com.cognitect/transit-cljs "0.8.239"]
                 [com.cognitect/transit-clj "0.8.300"]

                 [fast-zip "0.7.0"]

                 [re-view "0.3.20-SNAPSHOT"]
                 [re-view-routing "0.1.3"]
                 [re-view-material "0.1.6-SNAPSHOT"]
                 [cljs-live "0.2.1-SNAPSHOT"]
                 [magic-tree "0.0.7-SNAPSHOT"]
                 [org.clojure/data.json "0.2.6"]


                 [cljsjs/codemirror "5.19.0-0"]
                 [cljsjs/marked "0.3.5-0"]
                 [cljsjs/react "15.5.4-0"]
                 [cljsjs/react-dom "15.5.4-0"]
                 [cljsjs/firebase "4.0.0-0"]



                 ;; just for bundles
                 [reagent "0.7.0" :exclusions [cljsjs/react]]
                 ]

  :plugins [[lein-figwheel "0.5.10"]
            [lein-cljsbuild "1.1.6" :exclusions [org.clojure/clojure]]]

  :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                    "target"]

  :source-paths ["src" "script" "bundles"]

  :cljsbuild {:builds [{:id           "user-dev"
                        :source-paths ["src"]
                        :figwheel     true
                        :compiler     {:main           "maria.frames.user"
                                       :output-to      "resources/public/js/compiled/user.js"
                                       :output-dir     "resources/public/js/compiled/out-user-dev"
                                       :asset-path     "/js/compiled/out-user-dev"
                                       :source-map     true
                                       :optimizations  :none
                                       :parallel-build true}}
                       {:id           "user-prod"
                        :source-paths ["src"]
                        :compiler     {:main           "maria.frames.user"
                                       :output-to      "resources/public/js/compiled/user.js"
                                       :output-dir     "resources/public/js/compiled/out-user-prod"
                                       :asset-path     "/js/compiled/out-user-prod"
                                       :cache-analysis false
                                       :dump-core      false
                                       :parallel-build true
                                       :optimizations  :simple}}
                       {:id           "trusted-dev"
                        :source-paths ["src"]
                        :figwheel     true
                        :compiler     {:main           "maria.frames.trusted"
                                       :output-to      "resources/public/js/compiled/trusted.js"
                                       :output-dir     "resources/public/js/compiled/out-trusted-dev"
                                       :asset-path     "/js/compiled/out-trusted-dev"
                                       :source-map     true
                                       :optimizations  :none
                                       :parallel-build true}}
                       {:id           "trusted-prod"
                        :source-paths ["src"]
                        :compiler     {:main           "maria.frames.trusted"
                                       :output-to      "resources/public/js/compiled/trusted.js"
                                       :output-dir     "resources/public/js/compiled/out-trusted-prod"
                                       :asset-path     "/js/compiled/out-trusted-prod"
                                       :optimizations  :advanced
                                       :dump-core      false
                                       :parallel-build true}}]}

  :figwheel {:ring-handler figwheel-server.core/handler
             :css-dirs     ["resources/public/css"]
             :nrepl-port   7888}

  :aliases {"dev"           ["figwheel" "user-dev" "trusted-dev"]
            "build-web"     ["cljsbuild" "once" "user-prod" "trusted-prod"]
            "build-bundles" ["with-profile" "bundles" "run" "live-deps.clj"]
            }

  :deploy-via :clojars

  :profiles {:dev     {:dependencies [[figwheel-pushstate-server "0.1.1-SNAPSHOT"]
                                      [binaryage/devtools "0.8.2"]
                                      [figwheel-sidecar "0.5.10"]
                                      [com.cemerick/piggieback "0.2.1"]]
                       ;; need to add dev source path here to get user.clj loaded
                       :source-paths ["src" "dev"]
                       ;; for CIDER
                       ;:plugins      [[cider/cider-nrepl "0.14.0"]]
                       :repl-options {; for nREPL dev you really need to limit output
                                      :init             (set! *print-length* 50)
                                      :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}
             :bundles {:main build.live-deps}})
