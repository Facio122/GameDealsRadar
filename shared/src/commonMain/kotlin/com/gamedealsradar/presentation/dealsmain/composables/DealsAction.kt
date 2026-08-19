package com.gamedealsradar.presentation.dealsmain.composables

sealed interface DealsAction {

    data object OnPillsClicked : DealsAction
    data object OnSearchFocus : DealsAction
//    data object OnFilterDialogDismiss : DealsAction
}