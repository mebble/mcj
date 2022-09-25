(defproject mcj "0.1.0-SNAPSHOT"
  :plugins [[lein-eftest "0.5.9"]
            [lein-environ "1.2.0"]]
  :description "A math CLI tool in Clojure."
  :url "https://github.com/mebble/mcj"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [funcool/cats "2.4.2"]
                 [tortue/spy "2.13.0"]
                 [environ "1.2.0"]]
  :main ^:skip-aot mcj.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
