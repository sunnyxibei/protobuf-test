import com.google.gson.Gson
import me.sunnyxibei.data.EegInfo
import me.sunnyxibei.data.TaskProto
import me.sunnyxibei.data.TaskRecordRequest
import java.util.*
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main() {

    testSerialize(100000)

    println("*************************")

    testDeserialize(100000)
}

/**
 * 测试序列化的时间和空间
 */
private fun testSerialize(repeatCount: Int) {
    val gsonData = mockEegRecordOfGson(25)

    val protobufData = mockEegRecordOfProtobuf(25)

    val gsonTime = measureTimeMillis {
        repeat(repeatCount) {
            val toJson = Gson().toJson(gsonData).toByteArray()
//            println("Gson序列化大小 = ${toJson.size} bytes")
        }
    }
    println("Gson序列化时间 = $gsonTime ms")

    val protobufTime = measureTimeMillis {
        repeat(repeatCount) {
            val contentBytes = protobufData.toByteArray()
//            println("protobuf序列化大小 = ${contentBytes.size} bytes")
        }
    }
    println("protobuf序列化时间 = $protobufTime ms")
}

/**
 * * 测试反序列化的时间和空间
 */
private fun testDeserialize(repeatCount: Int) {
    val gsonData = Gson().toJson(mockEegRecordOfGson(25))

    val protobufData = mockEegRecordOfProtobuf(25).toByteArray()

    val gsonTime = measureTimeMillis {
        repeat(repeatCount) {
            Gson().fromJson(gsonData, TaskRecordRequest::class.java)
        }
    }
    println("Gson反序列化时间 = $gsonTime ms")

    val protobufTime = measureTimeMillis {
        repeat(repeatCount) {
            TaskProto.TaskRecord.parseFrom(protobufData)
        }
    }
    println("protobuf反序列化时间 = $protobufTime ms")
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
private fun mockEegRecordOfProtobuf(presetTime: Int): TaskProto.TaskRecord {
    val count = presetTime * 60
    val timestampData = generateSequence(0L) {
        it * 1000
    }.take(count).toList()
    val attentionData = generateSequence(0.0) {
        Random.nextDouble(100.0)
    }.take(count).toList()
    val eeg = TaskProto.Eeg.newBuilder()
        .addAllAlphaData(listOf())
        .addAllBetaData(listOf())
        .addAllAttentionData(attentionData)
        .addAllTimestampData(timestampData)
        .setStartTimestamp(System.currentTimeMillis())
        .setEndTimestamp(System.currentTimeMillis())
        .build()
    return TaskProto.TaskRecord.newBuilder()
        .setLocalId(UUID.randomUUID().toString())
        .setLocalCreated(System.currentTimeMillis())
        .setScore(Random.nextInt(until = 100))
        .setOriginDuration(presetTime * 60 * 1000L)
        .setSubject("数学")
        .setContent("作业")
        .setEeg(eeg)
        .build()
}