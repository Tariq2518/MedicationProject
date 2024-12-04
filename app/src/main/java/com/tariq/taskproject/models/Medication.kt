package com.tariq.taskproject.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
@Entity(tableName = "medications",
    indices = [Index(value = ["name", "dose", "strength", "problem"], unique = true)]
)
data class Medication(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val dose: String,
    val strength: String,
    val problem: String
): Parcelable{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Medication

        return name == other.name &&
                dose == other.dose &&
                strength == other.strength &&
                problem == other.problem
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + dose.hashCode()
        result = 31 * result + strength.hashCode()
        result = 31 * result + problem.hashCode()
        return result
    }
}

data class MedicalProblemsResponse(
    val problems: List<Map<String, List<MedicalCondition>>>
)

data class MedicalCondition(
    val medications: List<MedicationInfo>? = null,
    val labs: List<Map<String, String>>? = null
)

data class MedicationInfo(
    val medicationsClasses: List<Map<String, List<Map<String, List<Drug>>>>>? = null
)


data class Drug(
    val name: String? = null,
    val dose: String? = null,
    val strength: String? = null
)

