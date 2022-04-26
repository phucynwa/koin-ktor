package org.koin.ktor.ext

import io.ktor.server.testing.testApplication
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import org.koin.core.context.GlobalContext
import org.koin.core.context.loadKoinModules
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.dsl.single
import org.koin.java.KoinJavaComponent.getKoin

/**
 * @author vinicius
 * @author Victor Alenkov
 *
 */
class Foo(val name: String = "")
class Bar(val name: String = "")
class Bar2(val name: String = "")

class KoinFeatureTest {

    @Test
    fun `can install feature`() {

        class Foox(val name: String = "")
        val module = module(true) {
            single { Foox("") }
        }
        testApplication {

            install(KoinPlugin) {
                +module
            }

            application {
                val bean = get<Foox>()
                assertNotNull(bean)
            }
        }
    }

    @Test
    fun `Koin does not contain modules`() {
        testApplication {
            assertNull(GlobalContext.getOrNull())
            install(KoinPlugin)
            val koin = GlobalContext.getOrNull()
            assertNotNull(koin)
            requireNotNull(koin)

            assertNull(koin.getOrNull<Foo>())
        }
    }

    @Test
    fun `add a Koin module to an already running application`() {
        testApplication {
            install(KoinPlugin)
            val koin = getKoin()

            assertNull(koin.getOrNull<Foo>())

            loadKoinModules(module {
                singleOf(::Foo)
            })
            assertNotNull(koin.getOrNull<Foo>())
        }
    }

    @Test
    fun `Using the koin extension`() {
        testApplication {
            assertNull(GlobalContext.getOrNull())

            koinApplication {
                modules(module {
                    single<Foo>()
                })
            }
            assertNotNull(getKoin().getOrNull<Foo>())
        }
    }

    @Test
    fun `Using the koinModules extension`() {
        testApplication {
            assertNull(GlobalContext.getOrNull())

            loadKoinModules(
                listOf(
                module {
                    single<Foo>()
                },
                module {
                    single<Bar>()
                })
            )
            assertNotNull(getKoin().getOrNull<Foo>())
            assertNotNull(getKoin().getOrNull<Bar>())
        }
    }

//    @Test
//    fun `Using the koin extension (with pre-installation of the module)`() {
//        withApplication {
//            assertNull(GlobalContext.getOrNull())
//            assertNull(application.featureOrNull(KoinPlugin))
//
//            application.install(KoinPlugin)
//            assertNotNull(GlobalContext.getOrNull())
//            assertNotNull(application.featureOrNull(KoinPlugin))
//            assertNull(application.getKoin().getOrNull<Foo>())
//
//            application.koin {
//                loadKoinModules(module {
//                    single<Foo>()
//                })
//            }
//            assertNotNull(application.getKoin().getOrNull<Foo>())
//        }
//    }
//
//    @Test
//    @Ignore
//    fun `Create required beans`() {
//        withApplication {
//            var s = "zero"
//
//            application.koin {
//                modules(module {
//                    single {
//                        s = "two"
//                        newInstance<Foo>(emptyParametersHolder())
//                    }
//                })
//            }
//            assertEquals("zero", s)
//
//            application.koin {
//                modules(module {
//                    single(createdAtStart = true) {
//                        s = "one"
//                        newInstance<Bar>(emptyParametersHolder())
//                    }
//                    single {
//                        s = "three"
//                        newInstance<Bar2>(emptyParametersHolder())
//                    }
//                })
//                createEagerInstances()
//            }
//
//            assertEquals("one", s)
//
//            assertNotNull(application.getKoin().getOrNull<Foo>())
//            assertEquals("two", s)
//
//            assertNotNull(application.getKoin().getOrNull<Bar>())
//            assertEquals("two", s)
//
//            assertNotNull(application.getKoin().getOrNull<Bar2>())
//            assertEquals("three", s)
//        }
//    }
//
//    @Suppress("EXPERIMENTAL_API_USAGE_FUTURE_ERROR")
//    @Test
//    fun `custom stop listeners`() {
//        val module = module {
//            single { Foo("bar") }
//        }
//        var c = 0
//        withTestApplication {
//            assertEquals(0, c)
//
//            val monitor = environment.monitor
//            monitor.subscribe(KoinApplicationStopPreparing) {
//                c += 1
//            }
//            monitor.subscribe(KoinApplicationStopped) {
//                c += 2
//            }
//
//            application.install(KoinPlugin) {
//                modules(module)
//            }
//            val bean = GlobalContext.get().get<Foo>()
//            assertNotNull(bean)
//            assertEquals(0, c)
//        }
//        assertEquals(3, c)
//
//        withTestApplication {
//            assertEquals(3, c)
//            application.install(KoinPlugin) {
//                modules(module)
//                environment.monitor.subscribe(KoinApplicationStarted) {
//                    c = 0
//                }
//                environment.monitor.subscribe(KoinApplicationStopped) {
//                    c += 4
//                }
//            }
//            val bean = GlobalContext.get().get<Foo>()
//            assertNotNull(bean)
//            assertEquals(0, c)
//        }
//        assertEquals(4, c)
//    }
}
