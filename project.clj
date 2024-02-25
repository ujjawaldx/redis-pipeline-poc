(defproject redis-pipeline-poc "0.1.0"
  :description "Project to benchmark redis pipelining"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [com.taoensso/carmine "3.3.2"]
                 [criterium "0.4.6"]]
  :local-repo ".local-m2"
  :main ^:skip-aot redis-pipeline-poc.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
