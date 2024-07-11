import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.api.core.HttpClient
import com.example.api.core.RequestResult
import com.example.api.interfaces.ITorneioService
import com.example.mytennis.R
import com.example.mytennis.adapter.TournamentAdapter
import responses.torneios.GetAllTorneiosResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TournamentListFragment : Fragment() {

    private lateinit var torneioService: ITorneioService
    private lateinit var adapter: TournamentAdapter
    private lateinit var recyclerView: RecyclerView
    private var torneios: List<Torneio> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.tournament_list, container, false)
        recyclerView = view.findViewById(R.id.tournament_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        torneioService = HttpClient(ITorneioService::class.java).api

        loadTorneios()

        return view
    }

    private fun loadTorneios() {
        torneioService.getTorneios().enqueue(object :
            Callback<RequestResult.Success<GetAllTorneiosResponse>> {
            override fun onResponse(call: Call<RequestResult.Success<GetAllTorneiosResponse>>, response: Response<RequestResult.Success<GetAllTorneiosResponse>>) {
                if (response.isSuccessful) {
                    torneios = response.body()?.data?.torneios ?: emptyList()
                    adapter = TournamentAdapter(torneios)
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(requireContext(), "Erro ao carregar torneios", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RequestResult.Success<GetAllTorneiosResponse>>, t: Throwable) {
                Toast.makeText(requireContext(), "Erro: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
