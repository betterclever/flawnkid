package com.freactive.flawnkid.core.interfaces

import com.freactive.flawnkid.core.util.BaseIconProvider

interface AbstractApp {
    var label: String
    var packageName: String
    var className: String
    var iconProvider: BaseIconProvider
}
