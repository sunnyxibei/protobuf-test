import me.sunnyxibei.data.*
import java.util.*
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main() {

    val gsonTime = measureTimeMillis {
        //todo Gson序列化
        mockEegRecordOfGson(25)
    }

    println("gsonTime = $gsonTime")

    val protobufTime = measureTimeMillis {
        //todo protobuf序列化
        mockEegRecordOfProtobuf(25)
    }
    println("protobufTime = $protobufTime")

}

/**
 * mock一段指定时长的eeg训练记录 Gson
 * @param presetTime 预设时长，单位分钟
 */
private fun mockEegRecordOfGson(presetTime: Int): TaskRecordRequest {
    val count = presetTime * 60
    val timestampData = generateSequence(0L) {
        it * 1000
    }.take(count).toList()
    val attentionData = generateSequence(0.0) {
        Random.nextDouble(100.0)
    }.take(count).toList()
    val eeg = EegInfo(
        alphaData = listOf(),
        betaData = listOf(),
        attentionData = attentionData,
        timestampData = timestampData,
        startTimestamp = System.currentTimeMillis(),
        endTimestamp = System.currentTimeMillis(),
    )
    return TaskRecordRequest(
        localId = UUID.randomUUID().toString(),
        localCreated = System.currentTimeMillis(),
        localUpdated = System.currentTimeMillis(),
        score = Random.nextInt(until = 100),
        originDuration = presetTime * 60 * 1000L,
        subject = "数学",
        content = "作业",
        eeg = eeg,
    )
}

/**
 * mock一段指定时长的eeg训练记录 Protobuf
 * @param presetTime 预设时长，单位分钟
 */
private fun mockEegRecordOfProtobuf(presetTime: Int) {
    val count = presetTime * 60
    val timestampData = generateSequence(0L) {
        it * 1000
    }.take(count).toList()
    val attentionData = generateSequence(0.0) {
        Random.nextDouble(100.0)
    }.take(count).toList()
    val eeg = EegInfo(
        alphaData = listOf(),
        betaData = listOf(),
        attentionData = attentionData,
        timestampData = timestampData,
        startTimestamp = System.currentTimeMillis(),
        endTimestamp = System.currentTimeMillis(),
    )
    TaskRecordRequest(
        localId = UUID.randomUUID().toString(),
        localCreated = System.currentTimeMillis(),
        localUpdated = System.currentTimeMillis(),
        score = Random.nextInt(until = 100),
        originDuration = presetTime * 60 * 1000L,
        subject = "数学",
        content = "作业",
        eeg = eeg,
    )
}