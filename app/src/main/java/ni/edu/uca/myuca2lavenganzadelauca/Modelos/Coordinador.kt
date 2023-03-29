package ni.edu.uca.myuca2lavenganzadelauca.Modelos

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import java.time.LocalDate

class Coordinador(
    var idC: Int,
    var nombres: String,
    var apellidos: String,
    var fechaNac: LocalDate,
    var titulo: String,
    var email: String,
    var facultad: String
) : Parcelable{
    @RequiresApi(Build.VERSION_CODES.O)
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        LocalDate.parse(parcel.readString()!!),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idC)
        parcel.writeString(nombres)
        parcel.writeString(apellidos)
        parcel.writeString(titulo)
        parcel.writeString(email)
        parcel.writeString(facultad)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Coordinador> {
        override fun createFromParcel(parcel: Parcel): Coordinador {
            return Coordinador(parcel)
        }

        override fun newArray(size: Int): Array<Coordinador?> {
            return arrayOfNulls(size)
        }
    }
}