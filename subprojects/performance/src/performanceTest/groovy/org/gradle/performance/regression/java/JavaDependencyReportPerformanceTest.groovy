/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.performance.regression.java

import org.gradle.performance.AbstractCrossVersionPerformanceTest
import org.gradle.performance.annotations.RunFor
import org.gradle.performance.annotations.Scenario
import spock.lang.Unroll

import static org.gradle.performance.annotations.ScenarioType.PER_DAY
import static org.gradle.performance.generator.JavaTestProjectGenerator.LARGE_JAVA_MULTI_PROJECT
import static org.gradle.performance.results.OperatingSystem.LINUX

@RunFor(
    @Scenario(type = PER_DAY, operatingSystems = [LINUX], testProjects = ["largeJavaMultiProject", "largeMonolithicJavaProject"])
)
class JavaDependencyReportPerformanceTest extends AbstractCrossVersionPerformanceTest {

    @Unroll
    def "generate dependency report"() {
        given:
        def subProject = (runner.testProject == LARGE_JAVA_MULTI_PROJECT.projectName) ? 'project363:' : ''
        runner.tasksToRun = ["${subProject}dependencyReport"]
        runner.targetVersions = ["7.1-20210412220040+0000"]

        when:
        def result = runner.run()

        then:
        result.assertCurrentVersionHasNotRegressed()
    }
}
