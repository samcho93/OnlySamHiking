package com.onlysamhiking.app.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.onlysamhiking.app.R
import com.onlysamhiking.app.data.model.HikingRecord
import com.onlysamhiking.app.data.repository.HikingRepository
import com.onlysamhiking.app.databinding.ActivityHistoryBinding
import com.onlysamhiking.app.ui.detail.HikingDetailActivity
import com.onlysamhiking.app.ui.`import`.ImportGpxActivity
import com.onlysamhiking.app.util.LocationUtils
import kotlinx.coroutines.*
import java.util.*

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var adapter: HistoryAdapter
    private lateinit var repository: HikingRepository
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private var currentTab = TAB_APP_RECORDS
    private var currentObserver: LiveData<List<HikingRecord>>? = null

    private val importLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            // 가져오기 완료 시 사용자 등록 탭 새로고침 (LiveData가 자동 반영)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = HikingRepository(this)

        binding.toolbar.setNavigationOnClickListener { finish() }

        adapter = HistoryAdapter(
            onClick = { record -> openDetail(record) },
            onDelete = { record -> showDeleteDialog(record) }
        )

        binding.rvHistory.layoutManager = LinearLayoutManager(this)
        binding.rvHistory.adapter = adapter

        setupTabs()

        // FAB for import GPX
        binding.fabImportGpx.setOnClickListener {
            val intent = Intent(this, ImportGpxActivity::class.java)
            importLauncher.launch(intent)
        }

        // 기본: 앱 기록 탭
        observeRecords(TAB_APP_RECORDS)
    }

    private fun setupTabs() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.tab_app_records))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(R.string.tab_user_records))

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabIndex = tab?.position ?: 0
                currentTab = tabIndex
                observeRecords(tabIndex)

                // 사용자 등록 탭에서만 FAB 표시
                binding.fabImportGpx.visibility =
                    if (tabIndex == TAB_USER_RECORDS) View.VISIBLE else View.GONE
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun observeRecords(tabIndex: Int) {
        // 이전 observer 제거
        currentObserver?.removeObservers(this)

        val liveData = when (tabIndex) {
            TAB_APP_RECORDS -> repository.getAppRecords()
            TAB_USER_RECORDS -> repository.getUserImportedRecords()
            else -> repository.getAppRecords()
        }

        currentObserver = liveData

        liveData.observe(this) { records ->
            adapter.submitList(records)
            binding.tvEmpty.visibility = if (records.isEmpty()) View.VISIBLE else View.GONE
            binding.rvHistory.visibility = if (records.isEmpty()) View.GONE else View.VISIBLE
            updateStatsHeader(records)
        }
    }

    private fun updateStatsHeader(records: List<HikingRecord>) {
        if (records.isEmpty()) {
            binding.statsHeader.visibility = View.GONE
            return
        }

        binding.statsHeader.visibility = View.VISIBLE

        // Total records
        binding.tvHeaderRecordCount.text = "${records.size}"

        // Total distance
        val totalDistance = records.sumOf { it.distance }
        binding.tvHeaderTotalDist.text = LocationUtils.formatDistance(totalDistance)

        // Monthly distance (current month)
        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentYear = calendar.get(Calendar.YEAR)
        val monthlyDistance = records.filter { rec ->
            val recCal = Calendar.getInstance().apply { timeInMillis = rec.startTime }
            recCal.get(Calendar.MONTH) == currentMonth && recCal.get(Calendar.YEAR) == currentYear
        }.sumOf { it.distance }
        binding.tvHeaderMonthDist.text = LocationUtils.formatDistance(monthlyDistance)

        // Unique mountain count (including duplicates as per spec: 중복 가능)
        val mountainCount = records.filter { it.mountainName.isNotEmpty() }
            .map { it.mountainName }
            .size
        binding.tvHeaderMountainCount.text = "$mountainCount"
    }

    private fun openDetail(record: HikingRecord) {
        val intent = Intent(this, HikingDetailActivity::class.java).apply {
            putExtra(HikingDetailActivity.EXTRA_RECORD_ID, record.id)
        }
        startActivity(intent)
    }

    private fun showDeleteDialog(record: HikingRecord) {
        AlertDialog.Builder(this)
            .setTitle(R.string.dialog_delete_title)
            .setMessage(R.string.dialog_delete_message)
            .setPositiveButton(R.string.dialog_delete) { _, _ ->
                scope.launch {
                    withContext(Dispatchers.IO) {
                        repository.deleteRecord(record)
                    }
                }
            }
            .setNegativeButton(R.string.dialog_cancel, null)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    companion object {
        private const val TAB_APP_RECORDS = 0
        private const val TAB_USER_RECORDS = 1
    }
}
