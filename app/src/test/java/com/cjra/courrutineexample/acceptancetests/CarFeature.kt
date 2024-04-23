package com.cjra.courrutineexample.acceptancetests

import com.cjra.courrutineexample.Car
import com.cjra.courrutineexample.Engine
import com.cjra.courrutineexample.utils.MainCoroutineScopeRule
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

class CarFeature {
    private val engine = Engine()

    private val car = Car(engine, 6.0)

    @get:Rule
    var coroutinesTestRule = MainCoroutineScopeRule()

    @Test
    fun carIsLoosingFuelWhenItTurnsOn() = runBlockingTest {
        car.turnOn()

        assertEquals(5.5, car.fuel)
    }

    @Test
    fun carIsTurningOnItsEngineAndIncreasesTheTemperature() = runBlockingTest {
        car.turnOn()
        assertEquals(15, car.engine.temperature)

        coroutinesTestRule.testScheduler.apply { advanceTimeBy(1000); runCurrent() }
        assertEquals(25, car.engine.temperature)
        coroutinesTestRule.testScheduler.apply { advanceTimeBy(1000); runCurrent() }
        assertEquals(50, car.engine.temperature)
        coroutinesTestRule.testScheduler.apply { advanceTimeBy(1000); runCurrent() }
        assertEquals(95, car.engine.temperature)

        assertTrue(car.engine.isTurnedOn)
    }
}