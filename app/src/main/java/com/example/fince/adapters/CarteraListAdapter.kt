import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.data.model.ActivoModel
import com.example.fince.databinding.ItemCarteraBinding
import com.example.fince.holders.CarteraHolder
import com.example.fince.listeners.OnViewItemClickedListenerCartera

class CarteraListAdapter (
    private val carteraList: List<ActivoModel>,
    private val onItemClick: OnViewItemClickedListenerCartera,
): RecyclerView.Adapter<CarteraHolder>(){

    private var activoCount: Int = 0
    override fun getItemCount(): Int {
        activoCount = carteraList.size
        return activoCount
    }
    fun setCarteraList(carteraList: List<ActivoModel>) {
        (this.carteraList as ArrayList).clear()
        this.carteraList.addAll(carteraList)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarteraHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCarteraBinding.inflate(inflater, parent, false)
        return (CarteraHolder(binding))
    }
    override fun onBindViewHolder(holder: CarteraHolder, position: Int) {

        val activo = carteraList.get(position)

        holder.setSimbol(activo.simbolo)
        holder.setCantidad(activo.cantidad.toString())
        holder.setPrecioDeCompra(activo.valorDeCompra.toString())
        holder.setPrecioActual(activo.valorActual.toString())

        holder.getCardLayout().setOnClickListener{
            onItemClick.onViewItemDetail(activo)
        }
    }
}