/*
 * Copyright 2017-2019 the original author or authors.
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
package org.koin.ktor.ext

/**
 * @author Arnaud Giuliani
 * @author Vinicius Carvalho
 * @author Victor Alenkov
 *
 * Ktor Feature class. Allows Koin Context to start using Ktor default install(<feature>) method.
 *
 */
//class Koin(internal val koinApplication: KoinApplication) {
//
//    companion object Feature : ApplicationPlugin<Application, KoinApplication, Koin> {
//        override val key = AttributeKey<Koin>("Koin")
//
//        override fun install(pipeline: Application, configure: KoinAppDeclaration): Koin {
//            val monitor = pipeline.environment.monitor
//
//            val koinApplication = startKoin(appDeclaration = configure)
//            monitor.raise(KoinApplicationStarted, koinApplication)
//
//            monitor.subscribe(ApplicationStopped) {
//                monitor.raise(KoinApplicationStopPreparing, koinApplication)
//                stopKoin()
//                monitor.raise(KoinApplicationStopped, koinApplication)
//            }
//
//            return Koin(koinApplication)
//        }
//    }
//}
//
///**
// * @author Victor Alenkov
// *
// * Gets or installs a [Koin] feature for the this [Application] and runs a [configuration] script on it
// */
//@ContextDsl
//fun Application.koin(configuration: KoinAppDeclaration) = featureOrNull(Koin)?.apply {
//    koinApplication.apply(configuration)
//} ?: install(Koin, configuration)
//
///**
// * @author Victor Alenkov
// *
// * Gets or installs a [Koin] feature for the this [Application] and install a [block] modules on it
// */
//@ContextDsl
//fun Application.modules(vararg block: Module) = koin { modules(block.asList()) }
