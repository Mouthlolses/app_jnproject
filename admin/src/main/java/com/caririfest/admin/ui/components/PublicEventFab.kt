package com.caririfest.admin.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Floating Action Button customizado para "Publicar Evento".
 *
 * Uso:
 * PublicarEventoFab(
 *   onClick = { /* publica evento */ },
 *   isLoading = viewModel.isPublishing,
 * )
 */
@Composable
fun PublicEventFab(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isLoading: Boolean = false,
    enabled: Boolean = true,
    // Visual customization
    gradientColors: List<Color> = listOf(Color(0xFF00C853), Color(0xFF00BFA5)),
    contentColor: Color = Color.White,
    disabledColor: Color = Color(0xFFBDBDBD),
    elevation: Dp = 12.dp,
    cornerRadius: Dp = 16.dp,
    minHeight: Dp = 56.dp,
    iconResId: Int = android.R.drawable.ic_menu_send, // default icon, replace with your drawable
    text: String = "Publicar Evento",
) {
    // Controls whether we show the label. Could be based on screen width or explicit param.
    // Here we show label when not loading and when there's enough horizontal space.
    var measuredWidth by remember { mutableStateOf(0) }
    val showLabel = !isLoading

    // Animate corner/width subtly when loading
    val animatedCorner by animateDpAsState(
        targetValue = if (isLoading) cornerRadius / 1.2f else cornerRadius,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    val currentGradient = if (enabled) gradientColors else listOf(disabledColor, disabledColor)

    Surface(
        modifier = modifier
            .onGloballyPositioned { coords ->
                measuredWidth = coords.size.width
            }
            .shadow(elevation = elevation, shape = RoundedCornerShape(animatedCorner))
            .semantics {
                // Accessibility label
                this.contentDescription =
                    if (enabled) "Botão publicar evento" else "Botão publicar evento desabilitado"
            }
            .clickable(
                enabled = enabled && !isLoading,
                onClick = onClick,
                role = Role.Button,
                indication = ripple(bounded = true),
                interactionSource = remember { MutableInteractionSource() }
            ),
        shape = RoundedCornerShape(animatedCorner),
        color = Color.Transparent // background drawn manually for gradient
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.horizontalGradient(currentGradient),
                    shape = RoundedCornerShape(animatedCorner)
                )
                .borderAndPadding(enabled, animatedCorner)
                .height(minHeight),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 14.dp)
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon or loading spinner
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(20.dp),
                        strokeWidth = 2.dp,
                        // color follows content color
                    )
                } else {
                    Icon(
                        painter = painterResource(id = iconResId),
                        contentDescription = null,
                        tint = contentColor,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Animated label visibility
                AnimatedVisibility(visible = showLabel) {
                    Spacer(modifier = Modifier.width(12.dp))
                }

                AnimatedVisibility(visible = showLabel) {
                    Text(
                        text = text,
                        fontSize = 16.sp,
                        color = contentColor,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

/**
 * Helper extension to add a subtle border/padding according to enabled state.
 */
private fun Modifier.borderAndPadding(enabled: Boolean, corner: Dp): Modifier {
    val border = if (enabled) BorderStroke(width = 1.dp, color = Color(0x25000000)) else null
    return this.then(
        Modifier
            .padding(2.dp)
            .then(if (border != null) Modifier else Modifier)
            .padding(horizontal = 2.dp)
    )
}

/**
 * Preview(s)
 */
@Preview(showBackground = true, widthDp = 360)
@Composable
private fun PreviewPublicarEventoFab_Default() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        PublicEventFab(
            onClick = { /*noop*/ },
            isLoading = false,
            enabled = true,
            iconResId = android.R.drawable.ic_menu_send, // preview icon
            text = "Publicar Evento"
        )
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun PreviewPublicarEventoFab_Loading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        PublicEventFab(
            onClick = { /*noop*/ },
            isLoading = true,
            enabled = false,
            iconResId = android.R.drawable.ic_menu_send,
            text = "Publicando..."
        )
    }
}