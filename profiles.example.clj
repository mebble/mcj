;; https://cljdoc.org/d/leiningen/leiningen/2.9.3/doc/profiles#declaring-profiles

{:dev  {:env {:project-url "example.com/dev"
              :app-version "dev-version"}}
 :test {:env {:project-url "example.com/test"
              :app-version "test-version"}}}
