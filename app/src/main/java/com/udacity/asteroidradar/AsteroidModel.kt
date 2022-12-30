package com.udacity.asteroidradar


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AsteroidModel(
    val element_count: Int?,
    val links: Links?,
    val near_earth_objects: NearEarthObjects?
) : Parcelable {
    @Parcelize
    data class Links(
        val next: String?,
        val previous: String?,
        val self: String?
    ) : Parcelable

    @Parcelize
    data class NearEarthObjects(
        val `2015-09-07`: List<X20150907?>?,
        val `2015-09-08`: List<X20150908?>?
    ) : Parcelable {
        @Parcelize
        data class X20150907(
            val absolute_magnitude_h: Double?,
            val close_approach_data: List<CloseApproachData?>?,
            val estimated_diameter: EstimatedDiameter?,
            val id: String?,
            val is_potentially_hazardous_asteroid: Boolean?,
            val is_sentry_object: Boolean?,
            val links: Links?,
            val name: String?,
            val nasa_jpl_url: String?,
            val neo_reference_id: String?
        ) : Parcelable {
            @Parcelize
            data class CloseApproachData(
                val close_approach_date: String?,
                val close_approach_date_full: String?,
                val epoch_date_close_approach: Long?,
                val miss_distance: MissDistance?,
                val orbiting_body: String?,
                val relative_velocity: RelativeVelocity?
            ) : Parcelable {
                @Parcelize
                data class MissDistance(
                    val astronomical: String?,
                    val kilometers: String?,
                    val lunar: String?,
                    val miles: String?
                ) : Parcelable

                @Parcelize
                data class RelativeVelocity(
                    val kilometers_per_hour: String?,
                    val kilometers_per_second: String?,
                    val miles_per_hour: String?
                ) : Parcelable
            }

            @Parcelize
            data class EstimatedDiameter(
                val feet: Feet?,
                val kilometers: Kilometers?,
                val meters: Meters?,
                val miles: Miles?
            ) : Parcelable {
                @Parcelize
                data class Feet(
                    val estimated_diameter_max: Double?,
                    val estimated_diameter_min: Double?
                ) : Parcelable

                @Parcelize
                data class Kilometers(
                    val estimated_diameter_max: Double?,
                    val estimated_diameter_min: Double?
                ) : Parcelable

                @Parcelize
                data class Meters(
                    val estimated_diameter_max: Double?,
                    val estimated_diameter_min: Double?
                ) : Parcelable

                @Parcelize
                data class Miles(
                    val estimated_diameter_max: Double?,
                    val estimated_diameter_min: Double?
                ) : Parcelable
            }

            @Parcelize
            data class Links(
                val self: String?
            ) : Parcelable
        }

        @Parcelize
        data class X20150908(
            val absolute_magnitude_h: Double?,
            val close_approach_data: List<CloseApproachData?>?,
            val estimated_diameter: EstimatedDiameter?,
            val id: String?,
            val is_potentially_hazardous_asteroid: Boolean?,
            val is_sentry_object: Boolean?,
            val links: Links?,
            val name: String?,
            val nasa_jpl_url: String?,
            val neo_reference_id: String?
        ) : Parcelable {
            @Parcelize
            data class CloseApproachData(
                val close_approach_date: String?,
                val close_approach_date_full: String?,
                val epoch_date_close_approach: Long?,
                val miss_distance: MissDistance?,
                val orbiting_body: String?,
                val relative_velocity: RelativeVelocity?
            ) : Parcelable {
                @Parcelize
                data class MissDistance(
                    val astronomical: String?,
                    val kilometers: String?,
                    val lunar: String?,
                    val miles: String?
                ) : Parcelable

                @Parcelize
                data class RelativeVelocity(
                    val kilometers_per_hour: String?,
                    val kilometers_per_second: String?,
                    val miles_per_hour: String?
                ) : Parcelable
            }

            @Parcelize
            data class EstimatedDiameter(
                val feet: Feet?,
                val kilometers: Kilometers?,
                val meters: Meters?,
                val miles: Miles?
            ) : Parcelable {
                @Parcelize
                data class Feet(
                    val estimated_diameter_max: Double?,
                    val estimated_diameter_min: Double?
                ) : Parcelable

                @Parcelize
                data class Kilometers(
                    val estimated_diameter_max: Double?,
                    val estimated_diameter_min: Double?
                ) : Parcelable

                @Parcelize
                data class Meters(
                    val estimated_diameter_max: Double?,
                    val estimated_diameter_min: Double?
                ) : Parcelable

                @Parcelize
                data class Miles(
                    val estimated_diameter_max: Double?,
                    val estimated_diameter_min: Double?
                ) : Parcelable
            }

            @Parcelize
            data class Links(
                val self: String?
            ) : Parcelable
        }
    }
}