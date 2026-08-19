package com.gamedealsradar.presentation.dealsmain

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import gamedealsradar.shared.generated.resources.Res
import gamedealsradar.shared.generated.resources.logo_transparent
import org.jetbrains.compose.resources.painterResource

@Composable
fun Logo() {
    Image(
        painter = painterResource(Res.drawable.logo_transparent),
        contentDescription = "Logo"
    )
}

@Preview
@Composable
fun LogoPreview() {
    Logo()
}