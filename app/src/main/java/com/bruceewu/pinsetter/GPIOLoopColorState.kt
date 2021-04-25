package com.bruceewu.pinsetter

enum class GPIOLoopColorState(val tag: String, val state: IOAllState) {
    RED("红灯", IOAllState(false, true, true, false, true, true, false, false)),
    GREEN("绿灯", IOAllState(true, true, false, true, true, false, false, false)),
    BLUE("蓝灯", IOAllState(false, true, false, false, true, false, false, false)),
    NONE("黑灯", IOAllState(false, false, false, false, false, false, false, false));

    fun next(): GPIOLoopColorState {
        return when (cur()) {
            RED -> GREEN
            GREEN -> BLUE
            BLUE -> RED
            NONE -> RED
        }
    }

    private fun cur(): GPIOLoopColorState = (values().firstOrNull {
        it.state.same(this@GPIOLoopColorState.state)
    } ?: NONE)
}

data class IOAllState(
    val io00: Boolean,
    val io01: Boolean,
    val io02: Boolean,
    val io03: Boolean,
    val io04: Boolean,
    val io05: Boolean,
    val io06: Boolean,
    val io07: Boolean
) {

    fun same(new: IOAllState): Boolean {
        return new.io00 == io00
                && new.io01 == io01
                && new.io02 == io02
                && new.io03 == io03
                && new.io04 == io04
                && new.io05 == io05
                && new.io06 == io06
                && new.io07 == io07
    }

    fun get(io: Int): Boolean = when (io) {
        0 -> io00
        1 -> io01
        2 -> io02
        3 -> io03
        4 -> io04
        5 -> io05
        6 -> io06
        7 -> io07
        else -> false
    }
}