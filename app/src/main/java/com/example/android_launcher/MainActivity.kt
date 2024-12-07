package com.example.android_launcher

import android.content.Intent
import android.content.pm.PackageManager
import android.health.connect.datatypes.AppInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.android_launcher.ui.theme.Android_LauncherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            M
        }
    }
}

@Composable
fun MyLauncher() {
    val context = LocalContext.current
    val packageManager = context.packageManager
    val apps = remember {
        packageManager.queryIntentActivities(
            Intent(Intent.ACTION_MAIN).apply { addCategory(Intent.CATEGORY_LAUNCHER) },
            PackageManager.MATCH_ALL
        ).map { resolveInfo ->
            AppInfo(
                name = resolveInfo.loadLabel(packageManager).toString(),
                icon = resolveInfo.loadIcon(packageManager),
                packageName = resolveInfo.activityInfo.packageName
            )
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(80.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(apps.size) { index ->
            AppIcon(app = apps[index]) {
                val intent = packageManager.getLaunchIntentForPackage(it.packageName)
                if (intent != null) {
                    context.startActivity(intent)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {}