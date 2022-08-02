package app.revanced.patches.youtube.misc.sigspoof.patch

import app.revanced.patcher.annotation.Description
import app.revanced.patcher.annotation.Name
import app.revanced.patcher.annotation.Version
import app.revanced.patcher.data.impl.ResourceData
import app.revanced.patcher.patch.PatchResult
import app.revanced.patcher.patch.PatchResultSuccess
import app.revanced.patcher.patch.annotations.Patch
import app.revanced.patcher.patch.impl.ResourcePatch
import app.revanced.patches.youtube.misc.sigspoof.annotations.SigSpoof
import org.w3c.dom.Element

@Patch(false)
@Name("signature-spoofing")
@Description("Enables signature spoofing for microG.")
@SigSpoof
@Version("0.0.1")
class SigSpoof : ResourcePatch() {
    override fun execute(data: ResourceData): PatchResult {
        
        data.xmlEditor["AndroidManifest.xml"].use {
            val manifestNode = it
                .file
                .getElementsByTagName("manifest")
                .item(0) as Element

            val element = it.file.createElement("uses-permission")
            element.setAttribute("android:name", "android.permission.FAKE_PACKAGE_SIGNATURE")
            manifestNode.appendChild(element)

            // get the application node
            val applicationNode = it
                .file
                .getElementsByTagName("application")
                .item(0) as Element

            // set application as debuggable
            val metaDataElement = it.file.createElement("meta-data")
            metaDataElement.setAttribute("android:name", "fake-signature")
            metaDataElement.setAttribute("android:value", "@string/fake_signature")
            applicationNode.appendChild(metaDataElement)
        }

        data.xmlEditor["res/values/strings.xml"].use {
            // get the resources node
            val resourcesNode = it
                .file
                .getElementsByTagName("resources")
                .item(0) as Element
            val stringElement = it.file.createElement("string")
            stringElement.setAttribute("name", "fake_signature")
            stringElement.appendChild(it.file.createTextNode("308202c3308201aba003020102020414be83f2300d06092a864886f70d01010b050030123110300e060355040313074c616e63686f6e301e170d3136313031313034303232335a170d3431313030353034303232335a30123110300e060355040313074c616e63686f6e30820122300d06092a864886f70d01010105000382010f003082010a0282010100b5bcd234cf0ef6db9c563577d7582157328b8e36033756f9729a69e3ae72159391e3a5e11b1e3055c970e4b3eb41d7ad633537d2cef81d9386674e8d01eac8a42e7b50041d1d533d38b38ac52d8a234256aa545ad4150a39d9c2a604bfe2893cf4dcb0dddd02064c9219d2100501d547f928b640cead0d62c7d2748ce357228d814ecfd6e9bb0e3e405f9b84db3aa820eeab89149ee652a3407fce0f3371da11d2f496553ca125d5d89e7795badae022661630ea8886708629e682b7232f93336f207523fa7dd755354828631bffd3dea21d73c07fe4e4024754f8f683be8ed66b37e8b0452d6ba0ac1a6dd839e7143d45f6f8f7e22b5c6978f9bbdefad0cb1b0203010001a321301f301d0603551d0e04160414422df1a2a9fca310062d24dede8e3ce164633c73300d06092a864886f70d01010b05000382010100301a0870b8f59617208161f2bdaadb73b96663be9330a676f2d65d515e8270f635f1a1e640fc62e1736ba74d8903c5580a2536fa4b1686b4db8404716a040779a84d59f5bc6dc8766666bf7515f144dfc328f48f4369e10e50a2fa3fc60d38fe9cd706d4d4c2d598f8994fb2e467f3e6f1750c69381e47a7f522a58aca8e65e3a06c155b0ed861f7e2478f48580a6a76505ea571a1a6bae86908fa4ceeb1ff6729592e91bdef42c6b9a4b98436275f8d35983eee66e2c3f02dbf850608efd462ee60a15f73deaf6fac1b2acaa2df9b5cca20c20150cb926d264f70381c2376b27734482a710eaa7c6c6c18bc6c8234751c1add194fbf6686ff2ec06ef0335f82"))
            resourcesNode.appendChild(stringElement)
        }

        return PatchResultSuccess()
    }
}
