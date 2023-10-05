package xyz.theprogramsrc.networkingmodule

import xyz.theprogramsrc.simplecoreapi.global.models.module.Module
import xyz.theprogramsrc.simplecoreapi.global.models.module.ModuleDescription

class NetworkingModule: Module {

    override val description: ModuleDescription =
        ModuleDescription(
            name = "@name@",
            version = "@version@",
            authors = listOf("Im-Fran")
        )

    override fun onDisable() {
        
    }

    override fun onEnable() {
        
    }
}