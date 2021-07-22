package data

data class TaskRecordRequest(
    val localId: String,
    val localCreated: Long,
    val localUpdated: Long,
    val score: Int,
    val originDuration: Long, //选择的时长：单位s
    val subject: String, //科目
    val content: String, //内容
    val eeg: EegInfo
)

data class EegInfo(
    val alphaData: List<Double>?,
    val betaData: List<Double>?,
    val attentionData: List<Double>,
    val timestampData: List<Long>,
    val startTimestamp: Long,
    val endTimestamp: Long
)