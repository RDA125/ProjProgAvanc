package com.example.projprogavanc.ui.gallery

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projprogavanc.*
import com.example.projprogavanc.databinding.FragmentGameStoreListBinding

/**
 * [Fragment] that will show the clients wishlist (Game_store table values)
 */
class WishlistFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    var selectedGameStore: GameStore? = null
        get() = field
        set(value){
            field = value
            (requireActivity() as MainActivity).ShowEditDeleteOptions(field != null)

        }

    //TODO("Don't forget about translations")
    private var _binding: FragmentGameStoreListBinding? = null
    private var _gameStoreAdapter : WishlistAdapter? = null

    private val gameStoreAdapter get() = _gameStoreAdapter!!
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //ViewModelProvider(this).get(WishlistViewModel::class.java)

        _binding = FragmentGameStoreListBinding.inflate(inflater, container, false)
        //val root: View = binding.root

        /*val textView: TextView = binding.textGallery
        galleryViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LoaderManager.getInstance(this).initLoader(LOADER_ID_GAMESTORE, null, this)

        _gameStoreAdapter = WishlistAdapter(this)
        binding.recViewWishlist.adapter = _gameStoreAdapter
        binding.recViewWishlist.layoutManager = LinearLayoutManager(requireContext())

        val activity = activity as MainActivity
        activity.fragment = this
        activity.currMenuId = R.menu.list_menu
        activity.updateTitle(getString(R.string.Wishlist_title))

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param id The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> =

        CursorLoader(
            requireContext(),
            ContentProviderGameStore.GAME_STORES_ADDRESS,
            TDBGame_Store.ALL_COLUMNS,
            null,
            null,
            "${TDBGame_Store.C_PRECO}"
        )



    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is *not* allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See [ FragmentManager.openTransaction()][androidx.fragment.app.FragmentManager.beginTransaction] for further discussion on this.
     *
     *
     * This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     *
     *  *
     *
     *The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a [android.database.Cursor]
     * and you place it in a [android.widget.CursorAdapter], use
     * the [android.widget.CursorAdapter.CursorAdapter] constructor *without* passing
     * in either [android.widget.CursorAdapter.FLAG_AUTO_REQUERY]
     * or [android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER]
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     *  *  The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a [android.database.Cursor] from a [android.content.CursorLoader],
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * [android.widget.CursorAdapter], you should use the
     * [android.widget.CursorAdapter.swapCursor]
     * method so that the old Cursor is not closed.
     *
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */
    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        gameStoreAdapter.cursor = data

    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */
    override fun onLoaderReset(loader: Loader<Cursor>) {
        if(_binding == null) return
        gameStoreAdapter.cursor = null
    }

    fun processOptionMenu(item: MenuItem): Boolean =
        when(item.itemId){
            R.id.action_insert -> {
                val action = WishlistFragmentDirections.actionWishlistToEditGameStoreFragment()
                (requireActivity() as MainActivity).updateTitle(getString(R.string.insertGameStore_title))
                findNavController().navigate(action)
                true
            }
            R.id.action_edit -> {
                val action = WishlistFragmentDirections.actionWishlistToEditGameStoreFragment(selectedGameStore)
                (requireActivity() as MainActivity).updateTitle(getString(R.string.EditGameStore_title))
                findNavController().navigate(action)
                true
            }
            R.id.action_delete -> {
                val action = WishlistFragmentDirections.actionWishlistToDeleteWishlistItem(selectedGameStore!!)
                findNavController().navigate(action)
                true
            }
            else -> false

        }


    companion object{

        const val LOADER_ID_GAMESTORE = 0

    }
}